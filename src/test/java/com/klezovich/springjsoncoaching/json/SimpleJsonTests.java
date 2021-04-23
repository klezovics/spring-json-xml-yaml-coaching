package com.klezovich.springjsoncoaching.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klezovich.springjsoncoaching.FileReader;
import lombok.*;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;


//TODO: Read this https://www.baeldung.com/jackson-object-mapper-tutorial
//TODO: Read about running asserts on json https://www.baeldung.com/jsonassert
public class SimpleJsonTests {

    private ObjectMapper mapper = new ObjectMapper();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        private Integer age;
    }

    @Test
    void testCanDeserializeObjectFromJson() throws JsonProcessingException {
        var user = mapper.readValue(FileReader.get("json/user.json"), User.class);
        assertEquals("AK",user.getName());
        assertEquals(31 ,user.getAge());
    }

    @Test
    void testCanSerealizeObjectToJson() throws JsonProcessingException, JSONException {
        var user = new User("AK",31);
        var userJson = mapper.writeValueAsString(user);
        JSONAssert.assertEquals(userJson, FileReader.get("json/user.json"), JSONCompareMode.STRICT);
    }
}
