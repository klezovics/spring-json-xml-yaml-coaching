package com.klezovich.springjsoncoaching.json.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonFilterTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @JsonFilter("passwordFilter")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        private String password;
        private String country;
    }

    @Test
    void testCanApplyPasswordFilter() throws JsonProcessingException {
        var user = User.builder().name("AK").password("pwd").country("DE").build();
        var filterProvider = new SimpleFilterProvider()
                .addFilter("passwordFilter", SimpleBeanPropertyFilter.serializeAllExcept("password"));

        var json = mapper.writer(filterProvider).writeValueAsString(user);

        //Check that password is filtered out
        assertThat(json, not(containsString("password")));
        assertThat(json, not(containsString("pwd")));

        //Check that everything else serialised
        assertThat(json, containsString("name"));
        assertThat(json, containsString("AK"));
        assertThat(json, containsString("country"));
        assertThat(json, containsString("DE"));
    }

}
