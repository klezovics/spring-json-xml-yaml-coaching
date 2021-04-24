package com.klezovich.springjsoncoaching.controller.customresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
public class ResponseController {

    @PostMapping
    public MyResponse getResponse(@RequestBody String body) {
        // Decide if response should be success or not
        boolean isSuccess = true;
        if (!isSuccess) {
            return createFailureResponse();
        }

        return createSuccessResponse();
    }

    private MyResponse createFailureResponse() {
        return MyResponse.builder()
                //Set all the fields here
                .status(MyResponseStatus.FAIL)
                .build();
    }

    private MyResponse createSuccessResponse() {
        return MyResponse.builder()
                .status(MyResponseStatus.SUCCESS)
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class MyResponse {
        private MyResponseStatus status;
        private MyResult result;
        private MyError error;
    }

    enum MyResponseStatus {
        SUCCESS, FAIL
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class MyResult {
        private String error;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class MyError {
        private String code;
        private String description;
        private String message;
    }
}
