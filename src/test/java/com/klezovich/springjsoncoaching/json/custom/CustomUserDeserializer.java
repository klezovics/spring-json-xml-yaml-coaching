package com.klezovich.springjsoncoaching.json.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.klezovich.springjsoncoaching.json.domain.User;

import java.io.IOException;

public class CustomUserDeserializer extends StdDeserializer<User> {

    public CustomUserDeserializer() {
        this(null);
    }

    public CustomUserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        User user = new User();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        JsonNode nameNode = node.get("imya_polzvatelja");
        String name = nameNode.asText();
        user.setName(name);

        JsonNode ageNode = node.get("vozrast");
        user.setAge(ageNode.asInt());
        return user;
    }
}