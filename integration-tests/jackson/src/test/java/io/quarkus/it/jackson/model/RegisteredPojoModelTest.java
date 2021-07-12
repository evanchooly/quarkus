package io.quarkus.it.jackson.model;

import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.it.kotser.model.RegisteredPojoModel;
import org.junit.jupiter.api.Test;

/**
 * Test cases for RegisteredPojoModel
 */
public class RegisteredPojoModelTest {

    // -------------------------------------------------------------------------
    // Test cases
    // -------------------------------------------------------------------------

    @Test
    public void testBuilderMinimal() {
        // prepare

        // execute
        io.quarkus.it.kotser.model.RegisteredPojoModel data = new io.quarkus.it.kotser.model.RegisteredPojoModel();

        // verify
        assertThat(data.getVersion()).isEqualTo(1);
        assertThat(data.getId()).isNull();
        assertThat(data.getValue()).isNull();
    }

    @Test
    public void testBuilder() {
        // prepare

        // execute
        io.quarkus.it.kotser.model.RegisteredPojoModel data = new RegisteredPojoModel();
        data.setVersion(2);
        data.setId("id1");
        data.setValue("value");

        // verify
        assertThat(data.getVersion()).isEqualTo(2);
        assertThat(data.getId()).isEqualTo("id1");
        assertThat(data.getValue()).isEqualTo("value");
    }
}
