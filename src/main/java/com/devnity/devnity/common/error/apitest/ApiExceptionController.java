package com.devnity.devnity.common.error.apitest;

import com.devnity.devnity.common.error.apitest.dto.TestRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1(@RequestBody TestRequest request) {
        return "ok";
    }
}
