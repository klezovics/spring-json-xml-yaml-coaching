package com.klezovich.springjsoncoaching.json.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.klezovich.springjsoncoaching.json.domain.User;
import lombok.SneakyThrows;

public class CustomUserSerializer extends StdSerializer<User> {

    public CustomUserSerializer() {
        this(null);
    }

    public CustomUserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    @SneakyThrows
    public void serialize(
            User user, JsonGenerator jsonGenerator, SerializerProvider serializer) {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("imya_polzvatelja", user.getName());
        jsonGenerator.writeNumberField("vozrast", user.getAge());
        jsonGenerator.writeEndObject();
    }
}