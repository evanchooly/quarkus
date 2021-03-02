package io.quarkus.mongodb.panache.kotlin.deployment;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import io.quarkus.gizmo.Gizmo;
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanionBase;
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepositoryBase;
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoCompanionBase;
import io.quarkus.panache.common.deployment.ByteCodeType;
import io.quarkus.panache.common.impl.GenerateBridge;
import io.quarkus.test.QuarkusUnitTest;

@SuppressWarnings("unchecked")
public class TestEnhancers {
    public static final ByteCodeType CLASS = new ByteCodeType(Class.class);
    public static final ByteCodeType GENERATE_BRIDGE = new ByteCodeType(GenerateBridge.class);
    public static final Pattern LABEL = Pattern.compile("^L(\\d)$");
    public static final ByteCodeType LONG = new ByteCodeType(long.class);
    public static final ByteCodeType OBJECT_ID = new ByteCodeType(ObjectId.class);
    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClasses(Widget.class)
                    .addClasses(ReactiveWidget.class)
                    .addClasses(Student.class)
                    .addClasses(StudentRepository.class));

    @Test
    public void testCompanion() {
        evaluate(Widget.Companion.getClass(), Widget.class, PanacheMongoCompanionBase.class, OBJECT_ID);
    }

    @Test
    public void testReactiveCompanion() {
        evaluate(ReactiveWidget.Companion.getClass(), ReactiveWidget.class, ReactivePanacheMongoCompanionBase.class, OBJECT_ID);
    }

    @Test
    public void testReactiveRepository() {
        evaluate(StudentRepository.class, Student.class, PanacheMongoRepositoryBase.class, LONG);
    }

    @Test
    public void testRepository() {
        evaluate(StudentRepository.class, Student.class, PanacheMongoRepositoryBase.class, LONG);
    }

    private void buildCatalog(ByteCodeType testType) {
        Map<String, BytecodeMethod> localMethods = findLocalMethods(testType);
        Map<String, List<String>> checks = new TreeMap<>();
        checks.put("INTRINSICS", new ArrayList<>());
        checks.put("IFNONNULL", new ArrayList<>());
//        checks.put("NOCHECKS", new ArrayList<>());
        for (Entry<String, BytecodeMethod> entry : localMethods.entrySet()) {
            boolean intrinsic = entry.getValue().body.stream()
                    .anyMatch(l -> l instanceof String && ((String) l).contains("checkNotNullExpressionValue"));
            boolean nonNull = entry.getValue().body.stream()
                    .anyMatch(l -> l instanceof String && ((String) l).contains("IFNONNULL"));
            if (intrinsic) {
                checks.get("INTRINSICS").add(entry.getKey());
            } else if (nonNull) {
                checks.get("IFNONNULL").add(entry.getKey());
//            } else {
//                checks.get("NOCHECKS").add(entry.getKey());
            }
        }
        trimCommon(checks);

        System.out.println(testType.dotName());
        StringJoiner joiner = new StringJoiner(" | ");
        int size = 70;
        checks.keySet().forEach(k -> joiner.add("%-" + size + "s"));
        System.out.printf(joiner + "%n", checks.keySet().toArray(new String[0]));
        int index = 0;
        int printed;
        do {
            printed = 0;
            StringJoiner line = new StringJoiner(" | ");
            List<String> methods = new ArrayList<>();
            for (List<String> value : checks.values()) {
                line.add("%-" + size + "s");

                String trim = trim(value, index, size);
                printed += trim.length();
                methods.add(trim);
            }
            System.out.printf(line + "%n", methods.toArray(new String[0]));
            index++;
        } while (printed != 0);
    }

    private void trimCommon(Map<String, List<String>> checks) {
        checks.forEach((key, value) -> {
            List<String> list = value.stream().filter(m -> !m.startsWith("target_"))
                    .collect(toList());

            list.forEach(m -> {
                if (value.contains("target_" + m)) {
                    value.remove(m);
                    value.remove("target_" + m);
                }
            });
        });
    }

    private void evaluate(Class<?> testClass, Class<?> entityClass, Class<?> baseType, ByteCodeType idType) {
        buildCatalog(new ByteCodeType(testClass));

        ByteCodeType object = new ByteCodeType(Object.class);
        ByteCodeType testType = new ByteCodeType(testClass);
        ByteCodeType entityType = new ByteCodeType(entityClass);
        Map<String, BytecodeMethod> localMethods = findLocalMethods(testType);

        Map<String, MethodNode> bridgeMethods = getBridgeMethods(baseType);
        checkForMissing(localMethods, bridgeMethods, idType, object, testType, entityType);

        localMethods.entrySet().stream()
                .filter(e -> e.getKey().startsWith("target_"))
                .forEach(entry -> {
                    String key = entry.getKey().substring(7);
                    try {
                        validate(key, localMethods.get(key).body, entry.getValue().body);
                    } catch (NullPointerException e) {
                        throw new RuntimeException("could not find " + key);
                    }
                });
    }

    private void checkForMissing(Map<String, BytecodeMethod> localMethods,
                                 Map<String, MethodNode> bridgeMethods,
                                 ByteCodeType idType,
                                 ByteCodeType object,
                                 ByteCodeType testType,
                                 ByteCodeType entityType) {
        List<String> missing = bridgeMethods.keySet().stream()
                                            .map(key -> {
                    if (key.contains("ById")) {
                        key = key.replace(object.descriptor(), idType.descriptor());
                        if (key.contains("findById") && !key.endsWith("Uni;")) {
                            key = key.substring(0, key.lastIndexOf(')') + 1) + entityType.descriptor();
                        }
                    }
                    return "target_" + key;
                })
                                            .filter(key -> localMethods.get(key) == null)
                                            .map(key -> String.format("%nCouldn't find %s in %s", key, testType.dotName()))
                                            .collect(Collectors.toList());
        assertTrue(missing.isEmpty(), missing.toString());
    }

    @NotNull
    private Map<String, BytecodeMethod> findLocalMethods(ByteCodeType byteCodeType) {
        try {
            Printer printer = new Textifier();
            String path = "/" + byteCodeType.internalName() + ".class";
            InputStream inputStream = config.getArchiveProducer().get().get(path).getAsset().openStream();
            new ClassReader(inputStream).accept(new TraceClassVisitor(
                    new ClassNode(Gizmo.ASM_API_VERSION), printer, null), ClassReader.EXPAND_FRAMES);
            Map<String, BytecodeMethod> methods = new TreeMap<>();
            ListIterator<Object> iterator = printer.text.listIterator();
            while (iterator.hasNext()) {
                Object t = iterator.next();
                if (t instanceof String) {
                    String line = (String) t;
                    if (line.trim().startsWith("// access flags") && !line.contains("synthetic bridge")) {
                        Object next = iterator.next();
                        if (next instanceof List) {
                            BytecodeMethod method = BytecodeMethod.parse(line, (List<Object>) next);
                            methods.put(method.name, method);
                        } else {
                            iterator.previous();
                        }
                    }
                }
            }
            return methods;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Map<String, MethodNode> getBridgeMethods(Class<?> type) {
        try {
            ClassNode node = new ClassNode(Gizmo.ASM_API_VERSION);
            new ClassReader(type.getName())
                    .accept(node, ClassReader.SKIP_FRAMES);
            Map<String, MethodNode> map = new TreeMap<>();
            node.methods.stream()
                    .filter(m -> m.visibleAnnotations != null && m.visibleAnnotations.stream()
                            .map(a -> a.desc)
                            .collect(toList())
                            .contains(GENERATE_BRIDGE.descriptor()))
                    .forEach(m -> map.put(m.name + m.desc, m));
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String trim(List<String> list, int index, int columnSize) {
        if (index >= list.size()) {
            return "";
        }
        String entry = list.get(index);
        if (entry.length() < columnSize) {
            return entry;
        }
        int endIndex = (columnSize/2) - 1;
        return entry.substring(0, endIndex) + "..." + entry.substring(entry.length() - endIndex + 1);
    }

    private void validate(String key, List<Object> actual, List<Object> expected) {
        assertEquals(cleanUp(expected), cleanUp(actual), format("%s does not match", key));
    }

    private static class BytecodeMethod {

        private final List<Object> body;

        private final String name;

        public BytecodeMethod(String name, List<Object> body) {
            this.name = name;
            this.body = body;
        }

        public static BytecodeMethod parse(String input, List<Object> body) {
            String[] split = input.trim().split("\\n");

            String[] nameSplit = split[split.length - 1].trim().split(" ");
            String name = nameSplit[nameSplit.length - 1];

            return new BytecodeMethod(name, body);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", BytecodeMethod.class.getSimpleName() + "[", "]")
                    .add("name = " + name)
                    .add("\nbody = " + body.toString())
                    .toString();
        }

    }

    private static String peek(List<Object> list, int index) {
        return (String) list.get(index + 1);
    }

    /**
     * we don't generate labels, e.g., like the compiler does (it's sourcemap related) so we need to elide some lines
     * to account for the difference.
     */
    private static List<Object> cleanUp(List<Object> input) {
        List<Object> results = new ArrayList<>(input);
        int index = 0;
        while (index < results.size()) {
            Object next = results.get(index);
            if (next instanceof String) {
                String line = ((String) next).trim();
                if (line.startsWith("LINENUMBER") || LABEL.matcher(line).matches()) {
                    results.remove(index);
                    continue;
                } else if (line.startsWith("LOCALVARIABLE")) {
                    results.set(index, line.replaceAll(" L\\d.*", "\n"));
                } else if (line.startsWith("LDC") && line.contains("INSTANCE.")) {
                    results.set(index, line.substring(0, line.indexOf("(") + 1) + "\n");
                } else if (line.startsWith("IFNONNULL") /*|| line.startsWith("LDC") && line.contains("\\u2026")*/) {
                    results.set(index, line.substring(0, line.indexOf(" ") + 1) + "\n");
//                } else if (line.startsWith("DUP")) {
//                    results.set(index, "RETURN TYPE NULL CHECK\n");
//                    try {
//                        while (!peek(results, index).trim().endsWith("RETURN")) {
//                            results.remove(index + 1);
//                        }
//                    } catch (IndexOutOfBoundsException e) {
//                        input.forEach(System.out::println);
//                        e.printStackTrace();
//                    }
                }
            }
            index++;
        }
        return results;
    }
}
