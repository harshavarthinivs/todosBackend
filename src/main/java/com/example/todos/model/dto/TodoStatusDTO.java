package com.example.todos.model.dto;

import com.example.todos.enums.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class TodoStatusDTO {
  
  TodoStatus status;

}
