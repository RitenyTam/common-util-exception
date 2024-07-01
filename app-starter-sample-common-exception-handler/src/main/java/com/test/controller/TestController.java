package com.test.controller;

import com.test.exception.TestViewCommonApiException;
import com.test.exception.TestApiCommonApiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/1")
    private String test1() {
        throw new TestApiCommonApiException("1", "test.index", "en_us");
    }

    @GetMapping("/2")
    private String test2() {
        throw new TestViewCommonApiException("1", "test.index2", "en_us");
    }
}
