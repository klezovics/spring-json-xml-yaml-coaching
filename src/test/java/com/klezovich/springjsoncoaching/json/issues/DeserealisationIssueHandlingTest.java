package com.klezovich.springjsoncoaching.json.issues;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeserealisationIssueHandlingTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @Data
    static class MyBean1 {
        private String name;
    }

    @Test
    void testCanDeserializeProperObject() throws JsonProcessingException {

        var bean = mapper.readValue("{\"name\": \"AK\"}",MyBean1.class);
        assertEquals("AK",bean.getName());
    }

    @Test
    void testCanDealWithAbsentProperty() throws JsonProcessingException {

        var bean = mapper.readValue("{}",MyBean1.class);
        assertNull(bean.getName());
    }

    @Data
    static class MyBean2 {
        @JsonProperty("imja")
        private String name;
    }

    @Test
    void testCanDealWithPropertyHavingADifferentName() throws JsonProcessingException {

        var bean = mapper.readValue("{\"imja\": \"AK\"}",MyBean2.class);
        assertEquals("AK", bean.getName());
    }

    @Test
    void testCanDealWithPropertyHavingADifferentType() throws JsonProcessingException {

        var bean = mapper.readValue("{\"name\": 12345}",MyBean1.class);
        assertEquals("12345", bean.getName());
    }

    @Data
    @JsonRootName(value = "data")
    static class MyBean3 {
        private String name;
    }

    @Test
    void testCanDealWithExtractingRootNode() throws JsonProcessingException {
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);

        var bean = mapper.readValue("{ \"data\": " +
                                            "  {\"name\": \"AK\"} " +
                                            "}",MyBean3.class);

        assertEquals("AK", bean.getName());
    }
}
