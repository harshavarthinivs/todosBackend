package com.example.todos.user;

import java.util.Set;
import com.example.todos.model.dto.TodoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  
  private Integer id;
  
  private String username;
  
  private String email;
  
  private String firstName;
  
  private String lastName;
  
  private Set<TodoResponse> todos;
}
