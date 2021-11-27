package com.devnity.devnity.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;

    @PostMapping("/api/test")
    public String insertDummy() {
        return testService.insertDummy();
    }

}
