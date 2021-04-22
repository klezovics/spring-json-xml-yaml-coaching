package com.klezovich.springjsoncoaching.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klezovich.springjsoncoaching.FileReader;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;


//TODO: Read this https://www.baeldung.com/jackson-object-mapper-tutorial
public class SimpleJsonTests {

    private ObjectMapper mapper = new ObjectMapper();

    @Data
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
}
