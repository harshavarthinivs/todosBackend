package com.example.todos.model.dto;

import com.example.todos.enums.Priority;
import com.example.todos.enums.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TodoResponse {
  
  private Integer id;
  
  private String title;
  
  private String description;
    
  private Priority priority;
  
  private TodoStatus status;
  
  private Integer userId; 
  
}
