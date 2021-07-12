package io.quarkus.it.jackson.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = io.quarkus.it.kotser.model.ModelWithJsonDeserializeUsing.CustomDeserializer.class)
public class ModelWithJsonDeserializeUsing {

    private String someValue;

    public ModelWithJsonDeserializeUsing() {
    }

    public ModelWithJsonDeserializeUsing(String someValue) {
        this.someValue = someValue;
    }

    public String getSomeValue() {
        return someValue;
    }

    public static class CustomDeserializer extends JsonDeserializer<io.quarkus.it.kotser.model.ModelWithJsonDeserializeUsing> {

        @Override
        public io.quarkus.it.kotser.model.ModelWithJsonDeserializeUsing deserialize(JsonParser p, DeserializationContext ctxt) {
            return new io.quarkus.it.kotser.model.ModelWithJsonDeserializeUsing("constant");
        }
    }
}
