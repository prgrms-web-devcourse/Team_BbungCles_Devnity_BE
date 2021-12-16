package com.devnity.devnity.common.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/health")
@RestController
public class HealthController {

  @GetMapping
  public ResponseEntity<String> healthCheck(){
    return ResponseEntity.ok("GOOD!!!");
  }

}
