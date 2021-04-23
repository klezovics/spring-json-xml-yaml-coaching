package com.klezovich.springjsoncoaching.controller;

import com.klezovich.springjsoncoaching.domain.MyUser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("russian")
class UserControllerAdvancedTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean
    private UserController userControllerSpy;

    @Test
    void testUserDeserealizedCorrectly() throws Exception {

        mvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"imja\":\"AK\", \"vozrast\": 31}")
        ).andExpect(status().isOk());

        var userCaptor = ArgumentCaptor.forClass(MyUser.class);
        verify(userControllerSpy).saveUser(userCaptor.capture());

        var user = userCaptor.getValue();
        assertEquals("AK", user.getName());
        assertEquals(31, user.getAge());
    }
}