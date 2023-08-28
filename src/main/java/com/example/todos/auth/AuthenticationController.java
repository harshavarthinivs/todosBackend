package com.example.todos.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todos.auth.dto.AuthenticationRequest;
import com.example.todos.auth.dto.AuthenticationResponse;
import com.example.todos.auth.dto.AuthenticationTokenDTO;
import com.example.todos.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
    
    AuthenticationResponse authenticationResponse = authenticationService.register(request);
    if (authenticationResponse.getClass() == AuthenticationTokenDTO.class)
      return ResponseEntity.ok(authenticationResponse);

    return ResponseEntity.status(409).body(authenticationResponse);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(authenticationService.authenticate(request));

  }
}
