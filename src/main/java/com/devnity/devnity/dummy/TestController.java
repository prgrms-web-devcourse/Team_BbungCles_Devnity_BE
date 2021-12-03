package com.devnity.devnity.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/test")
@RequiredArgsConstructor
@RestController
public class TestController {

  private final TestService testService;

  @PostMapping
  public Map<String, String> insertDummy() {
    return testService.insertDummy();
  }

  @PostMapping("/image")
  public Map<String, String> insertImage(@RequestBody ImageRequest request) {
    return testService.insertImage(request);
  }
}
