package com.klezovich.springjsoncoaching.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.klezovich.springjsoncoaching.domain.MyUser;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@JsonComponent
@Profile("russian")
public class MyUserDeserealizer extends JsonDeserializer<MyUser> {

    @Override
    public MyUser deserialize(JsonParser jsonParser,
                              DeserializationContext deserializationContext) throws IOException,
            JsonProcessingException {

        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        TextNode nameNode
                = (TextNode) treeNode.get("imja");
        IntNode ageNode
                = (IntNode) treeNode.get("vozrast");

        return new MyUser(nameNode.textValue(), ageNode.intValue());
    }
}