package com.klezovich.springjsoncoaching.controller;

import com.klezovich.springjsoncoaching.domain.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void saveUser(@RequestBody MyUser user) {
        log.info("{}", user);
    }
}
