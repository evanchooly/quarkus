package io.quarkus.it.jackson.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import io.quarkus.arc.Arc;

/**
 * Simple model class.
 */
@JsonPropertyOrder({ "version", "id", "value" })
@JsonDeserialize(builder = io.quarkus.it.kotser.model.InheritedModelWithBuilder.Builder.class)
public class InheritedModelWithBuilder extends io.quarkus.it.kotser.model.InheritedModelWithBuilderBase {

    // -------------------------------------------------------------------------
    // Object attributes
    // -------------------------------------------------------------------------

    private final String value;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    private InheritedModelWithBuilder(final Builder builder) {
        super(builder);
        this.value = builder.value;
    }

    // -------------------------------------------------------------------------
    // Interface
    // -------------------------------------------------------------------------

    public String toJson() throws IOException {
        return toJson(getObjectMapper());
    }

    public String toJson(ObjectMapper objectMapper) throws IOException {
        return objectMapper.writeValueAsString(this);
    }

    public static io.quarkus.it.kotser.model.InheritedModelWithBuilder fromJson(final String json) throws IOException {
        return getObjectMapper().readerFor(io.quarkus.it.kotser.model.InheritedModelWithBuilder.class).readValue(json);
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    @JsonProperty(value = "value")
    public String getValue() {
        return value;
    }

    // -------------------------------------------------------------------------
    // Inner classes
    // -------------------------------------------------------------------------

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
    public static final class Builder
            extends io.quarkus.it.kotser.model.InheritedModelWithBuilderBase.Builder<Builder, io.quarkus.it.kotser.model.InheritedModelWithBuilder> {

        // -------------------------------------------------------------------------
        // Object attributes
        // -------------------------------------------------------------------------

        protected String value = "";

        // -------------------------------------------------------------------------
        // Constructor
        // -------------------------------------------------------------------------

        public Builder(@JsonProperty(value = "id", required = true) final String id) {
            super(id);
        }

        public Builder(final io.quarkus.it.kotser.model.InheritedModelWithBuilder object) {
            super(object);
            this.value = object.value;
        }

        // -------------------------------------------------------------------------
        // Builder methods
        // -------------------------------------------------------------------------

        @JsonProperty(value = "value", required = true)
        public Builder withValue(final String value) {
            this.value = value;
            return this;
        }

        public io.quarkus.it.kotser.model.InheritedModelWithBuilder build() {
            return new io.quarkus.it.kotser.model.InheritedModelWithBuilder(this);
        }
    }

    private static ObjectMapper getObjectMapper() {
        return Arc.container().instance(ObjectMapper.class).get();
    }
}
