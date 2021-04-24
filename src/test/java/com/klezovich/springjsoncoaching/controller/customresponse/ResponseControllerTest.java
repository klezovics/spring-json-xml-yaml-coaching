package com.klezovich.springjsoncoaching.controller.customresponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klezovich.springjsoncoaching.controller.customresponse.ResponseController.MyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.klezovich.springjsoncoaching.controller.customresponse.ResponseController.MyResponseStatus.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ResponseControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSuccessfulResponseCreatedCorrectly() throws Exception {

        var result = mvc.perform(
                post("/response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"key1\": \"value1\"}")

        ).andDo(print()).andExpect(status().isOk()).andReturn();

        var responseObj = mapper.readValue(result.getResponse().getContentAsString(), MyResponse.class);
        assertEquals(SUCCESS, responseObj.getStatus());
        assertNull(responseObj.getError());
        assertNull(responseObj.getResult());
    }

    //TODO Create similar test for failed scenario
}