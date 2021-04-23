package com.klezovich.springjsoncoaching.json.jsonpath;

import com.jayway.jsonpath.JsonPath;
import com.klezovich.springjsoncoaching.util.FileReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Based on: https://www.baeldung.com/guide-to-jayway-jsonpath
public class JsonPathTest {

    private String json = FileReader.get("json/jsonpath/example.json");

    @Test
    void testCanQueryJsonDocument() {
        var creatorName = JsonPath.parse(json).read("$.tool.jsonpath.creator.name");
        assertEquals("Jayway Inc.", creatorName);

        var creatorFirstLocation = JsonPath.parse(json).read("$.tool.jsonpath.creator.location[0]");
        assertEquals("Malmo", creatorFirstLocation);

        List<String> creatorAllLocations = JsonPath.parse(json).read("$.tool.jsonpath.creator.location[*]");
        assertThat(creatorAllLocations, contains("Malmo","San Francisco","Helsingborg"));
    }
}
