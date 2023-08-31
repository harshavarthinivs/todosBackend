package com.example.todos.model.dto;

import com.example.todos.enums.Priority;
import com.example.todos.enums.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTodoRequest {
      
  private String title;
  
  private String description;
  
  private TodoStatus status;
  
  private Priority priority;
  
  private Integer userid;
}
