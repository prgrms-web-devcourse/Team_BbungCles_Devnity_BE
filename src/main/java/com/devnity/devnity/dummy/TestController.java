package com.devnity.devnity.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;

    @Value("${temp}")
    String temp;

    @PostMapping("/api/test")
    public String insertDummy() {
        System.out.println(temp);
        return temp;
    }

}
