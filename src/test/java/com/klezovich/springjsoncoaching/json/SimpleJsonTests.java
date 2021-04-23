package com.klezovich.springjsoncoaching.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.klezovich.springjsoncoaching.json.custom.CustomUserDeserializer;
import com.klezovich.springjsoncoaching.json.custom.CustomUserSerializer;
import com.klezovich.springjsoncoaching.json.domain.User;
import com.klezovich.springjsoncoaching.util.FileReader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


//TODO: Read this https://www.baeldung.com/jackson-object-mapper-tutorial
//TODO: Read about running asserts on json https://www.baeldung.com/jsonassert
@Slf4j
public class SimpleJsonTests {

    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    //Use case 1 - Create object from Json
    void testCanDeserializeObjectFromJson() throws JsonProcessingException {
        var user = mapper.readValue(FileReader.get("json/user.json"), User.class);
        assertEquals("AK", user.getName());
        assertEquals(31, user.getAge());
    }

    @Test
    //Use case 2 - Create Json from object
    void testCanSerealizeObjectToJson() throws JsonProcessingException, JSONException {
        var user = new User("AK", 31);
        var userJson = mapper.writeValueAsString(user);
        JSONAssert.assertEquals(userJson, FileReader.get("json/user.json"), JSONCompareMode.STRICT);
    }

    @Test
    //Use case 3 - Parse values from individual json fields
    void testCanGetIndividualJsonNodes() throws JsonProcessingException {
        var jsonNode = mapper.readTree(FileReader.get("json/user.json"));

        assertEquals("AK", jsonNode.get("name").asText());
    }

    @Test
    //Use case 4 - Parsing a list of items
    void testCanParseJsonList() throws JsonProcessingException {
        String userJsonArray = FileReader.get("json/user-list.json");
        var users = mapper.readValue(userJsonArray, new TypeReference<List<User>>() {
        });

        assertThat(users, hasSize(2));
        assertThat(users, contains(new User("AK", 31), new User("John", 99)));
    }

    @Test
    //Use case 5 - Parsing json into a map
    void testCanParseJsonIntoMap() throws JsonProcessingException {
        var user = mapper.readValue(FileReader.get("json/user.json"), new TypeReference<Map<String, Object>>() {
        });

        assertEquals("AK", user.get("name"));
        assertEquals(31, user.get("age"));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserEntity {
        @JsonIgnore
        private Long id;
        private String name;
    }

    @Test
    void testCanMarkPropertyAsIgnored() throws JsonProcessingException {
        var json = mapper.writeValueAsString(new UserEntity(1L, "AK"));

        log.info(json);
        assertThat(json, not(containsString("id")));
        assertThat(json, containsString("name"));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class UserEntity1 {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private Long id;
        private String name;
    }

    @Test
    void testCanIgnoreOnDeserealizationButNotOnSerialisation() throws JsonProcessingException {
        var json = mapper.writeValueAsString(new UserEntity1(1L, "AK"));

        log.info(json);
        assertThat(json, containsString("id"));
        assertThat(json, containsString("name"));

        var user = mapper.readValue(json, UserEntity1.class);
        assertNull(user.getId());
        assertEquals("AK", user.getName());
    }


    //OK, so the above covers the basics, let's move on to advanced features


    @Test
    //How can we handle a case, when json has more fields than the object we map it to ?
    void testCanDealWithUnknownFieldsDuringDeserialisation() throws JsonProcessingException {
        var json = FileReader.get("json/user-extra-field.json");

        //Will fail with com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
        //If you don't configure mapper like below
        // var user = mapper.readValue(json, User.class);

        //Configure mapper to ignore unknown properties
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var user = mapper.readValue(json, User.class);

        assertEquals("AK", user.getName());
        assertEquals(31, user.getAge());
    }


    @Test
    //How can we handle the case where field names in object and json are very different ?
    void canCreateCustomSerealizer() throws JsonProcessingException, JSONException {
        var module =
                new SimpleModule("CustomUserSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(User.class, new CustomUserSerializer());
        mapper.registerModule(module);


        var userCustomJson = mapper.writeValueAsString(new User("AK", 31));
        JSONAssert.assertEquals(userCustomJson, FileReader.get("json/user-custom.json"), JSONCompareMode.STRICT);
    }

    @Test
    //How can we handle the case where field names in object and json are very different ?
    void canCreateCustomDeserializer() throws JsonProcessingException, JSONException {
        var module =
                new SimpleModule("CustomUserDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(User.class, new CustomUserDeserializer());
        mapper.registerModule(module);

        var json = FileReader.get("json/user-custom.json");
        var user = mapper.readValue(json, User.class);

        assertEquals("AK", user.getName());
        assertEquals(31, user.getAge());
    }
}
