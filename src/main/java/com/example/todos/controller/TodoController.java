package com.example.todos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todos.model.dto.CreateTodoRequest;
import com.example.todos.model.dto.TodoStatusDTO;
import com.example.todos.service.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
  
  private final TodoService todoService;
  
  @PostMapping
  public ResponseEntity<Object> createTodo(@RequestBody CreateTodoRequest request) {
    
    var response = todoService.createTodo(request);
    if(response != null) {
      return ResponseEntity.status(201).body(response);
    }
    
    return ResponseEntity.badRequest().build();
  }
  
  @PatchMapping("/{id}/status")
  public ResponseEntity<Object> updateStatus(@RequestBody TodoStatusDTO request, @PathVariable String id) {
    
    var response  =todoService.updateStatus(request, id);
    if(response != null) return ResponseEntity.ok().body(response);
    
    return ResponseEntity.badRequest().build();
  }
  
  @PatchMapping("/{id}/todo")
  public ResponseEntity<Object> updateTodo(@RequestBody CreateTodoRequest request, @PathVariable String id) {
    
    var response =todoService.updateTodo(request,id);
    if(response != null) return ResponseEntity.ok().body(response);
    
    return ResponseEntity.badRequest().build();
  }
  @DeleteMapping("/{id}")
  public void deleteTodo(@PathVariable String id) {
    
    todoService.deleteTodo(id);
  }

}
