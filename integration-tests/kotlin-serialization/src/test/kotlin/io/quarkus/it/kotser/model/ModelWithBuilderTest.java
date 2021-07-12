package io.quarkus.it.kotser.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test cases for SensorData.Builder
 */
public class ModelWithBuilderTest {

    // -------------------------------------------------------------------------
    // Test cases
    // -------------------------------------------------------------------------

    @Test
    public void testBuilderMinimal() {
        // prepare
        io.quarkus.it.kotser.model.ModelWithBuilder.Builder builder = new io.quarkus.it.kotser.model.ModelWithBuilder.Builder(
                "id1");

        // execute
        io.quarkus.it.kotser.model.ModelWithBuilder data = builder.build();

        // verify
        assertThat(data.getVersion()).isEqualTo(1);
        assertThat(data.getId()).isEqualTo("id1");
        assertThat(data.getValue()).isEqualTo("");
    }

    @Test
    public void testBuilder() {
        // prepare
        io.quarkus.it.kotser.model.ModelWithBuilder.Builder builder = new io.quarkus.it.kotser.model.ModelWithBuilder.Builder(
                "id2")
                        .withVersion(2)
                        .withValue("value");

        // execute
        io.quarkus.it.kotser.model.ModelWithBuilder data = builder.build();

        // verify
        assertThat(data.getVersion()).isEqualTo(2);
        assertThat(data.getId()).isEqualTo("id2");
        assertThat(data.getValue()).isEqualTo("value");
    }

    @Test
    public void testBuilderCloneConstructor() {
        // prepare
        io.quarkus.it.kotser.model.ModelWithBuilder original = new io.quarkus.it.kotser.model.ModelWithBuilder.Builder("id1")
                .withVersion(3)
                .withValue("val")
                .build();

        // execute
        io.quarkus.it.kotser.model.ModelWithBuilder clone = new ModelWithBuilder.Builder(original).build();

        // verify
        assertThat(clone.getVersion()).isEqualTo(3);
        assertThat(clone.getId()).isEqualTo("id1");
        assertThat(clone.getValue()).isEqualTo("val");
    }
}
