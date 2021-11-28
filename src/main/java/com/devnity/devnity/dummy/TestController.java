package com.devnity.devnity.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;

    @PostMapping("/api/test")
    public Map<String, String> insertDummy() {
        return Map.of("result", testService.insertDummy());
    }

}
