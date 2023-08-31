package com.example.todos.auth.dto;

import com.example.todos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationTokenDTO implements AuthenticationResponse{
  
  private UserDTO data;
  private String token;
}
