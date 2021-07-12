package io.quarkus.it.kotser.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test cases for InheritedModelWithBuilder.Builder
 */
public class InheritedModelWithBuilderTest {

    // -------------------------------------------------------------------------
    // Test cases
    // -------------------------------------------------------------------------

    @Test
    public void testBuilderMinimal() {
        // prepare
        io.quarkus.it.kotser.model.InheritedModelWithBuilder.Builder builder = new io.quarkus.it.kotser.model.InheritedModelWithBuilder.Builder(
                "device1");

        // execute
        io.quarkus.it.kotser.model.InheritedModelWithBuilder data = builder.build();

        // verify
        assertThat(data.getVersion()).isEqualTo(1);
        assertThat(data.getId()).isEqualTo("device1");
        assertThat(data.getValue()).isEqualTo("");
    }

    @Test
    public void testBuilderUsingOptionals() {
        // prepare
        io.quarkus.it.kotser.model.InheritedModelWithBuilder.Builder builder = new io.quarkus.it.kotser.model.InheritedModelWithBuilder.Builder(
                "device1")
                        .withVersion(2)
                        .withValue("value");

        // execute
        io.quarkus.it.kotser.model.InheritedModelWithBuilder data = builder.build();

        // verify
        assertThat(data.getVersion()).isEqualTo(2);
        assertThat(data.getId()).isEqualTo("device1");
        assertThat(data.getValue()).isEqualTo("value");
    }

    @Test
    public void testBuilderCloneConstructor() {
        // prepare
        io.quarkus.it.kotser.model.InheritedModelWithBuilder original = new io.quarkus.it.kotser.model.InheritedModelWithBuilder.Builder(
                "device1")
                        .withValue("value")
                        .build();

        // execute
        io.quarkus.it.kotser.model.InheritedModelWithBuilder clone = new InheritedModelWithBuilder.Builder(original).build();

        // verify
        assertThat(clone.getVersion()).isEqualTo(1);
        assertThat(clone.getId()).isEqualTo("device1");
        assertThat(clone.getValue()).isEqualTo("value");
    }
}
