package com.klezovich.springjsoncoaching.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klezovich.springjsoncoaching.json.domain.ExtendableBean;
import com.klezovich.springjsoncoaching.json.domain.OrderedBean;
import com.klezovich.springjsoncoaching.json.domain.student.Diploma;
import com.klezovich.springjsoncoaching.json.domain.student.Student;
import com.klezovich.springjsoncoaching.util.FileReader;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;


//Based on https://www.baeldung.com/jackson-annotations
public class JacksonAnnotationsTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    //Use case - deserialize map in an elegant way
    void testCanDeserealizeMapProperties() throws JsonProcessingException, JSONException {
        ExtendableBean bean = new ExtendableBean("My bean");
        bean.getProperties().put("attr1", "val1");
        bean.getProperties().put("attr2", "val2");

        var json = mapper.writeValueAsString(bean);

        JSONAssert.assertEquals(FileReader.get("json/annotations/extendable-bean.json"), json, JSONCompareMode.STRICT);
    }

    @Test
    //Use case - reorder properties
    void testCanReorderPropertiesInJson() throws JsonProcessingException, JSONException {
        var bean = new OrderedBean("Kid",10);
        var json = mapper.writeValueAsString(bean);

        JSONAssert.assertEquals(FileReader.get("json/annotations/ordered-bean.json"), json, JSONCompareMode.STRICT);
    }

    @Test
    //Use case - add custom deserialisation for a type
    void testCanDeserealiseObjectToString() throws JsonProcessingException, JSONException {
        var diploma = Diploma.builder()
                .color("red").student(
                        Student
                        .builder()
                        .name("Taras")
                        .faculty("DGAP")
                        .averageGrade(5)
                        .build())
                .build();

        var json = mapper.writeValueAsString(diploma);

        JSONAssert.assertEquals(FileReader.get("json/annotations/diploma.json"), json, JSONCompareMode.STRICT);
    }
}
