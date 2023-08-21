package com.example.todos.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo-application")
public class demoController {
  
  @GetMapping
  public ResponseEntity<String> get() {
    return ResponseEntity.ok("Hello World");
  }
}
