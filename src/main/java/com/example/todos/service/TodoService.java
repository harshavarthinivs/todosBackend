package com.example.todos.service;

import org.springframework.stereotype.Service;
import com.example.todos.model.TodoDAO;
import com.example.todos.model.dto.CreateTodoRequest;
import com.example.todos.model.dto.TodoResponse;
import com.example.todos.model.dto.TodoStatusDTO;
import com.example.todos.repository.TodoRepository;
import com.example.todos.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;
  private final UserRepository userRepository;


  public TodoResponse createTodo(CreateTodoRequest request) {

    var user = userRepository.findById(request.getUserid());

    if (!user.isPresent())
      return null;

    var todo = TodoDAO.builder().title(request.getTitle()).description(request.getDescription())
        .priority(request.getPriority()).status(request.getStatus()).user(user.get()).build();

    todoRepository.save(todo);

    var savedTodo = todoRepository.findTopByUserOrderByIdDesc(todo.getUser());
    if (savedTodo.isPresent()) {
      var todoObject = savedTodo.get();
      return TodoResponse.builder().id(todoObject.getId()).priority(todoObject.getPriority())
          .status(todoObject.getStatus()).userId(todoObject.getUser().getId())
          .title(todoObject.getTitle()).description(todoObject.getDescription()).build();
    }
    return null;
  }
  
  /** 
   * Helper function used for  returning TodoResponse After updation 
   * */
  public TodoResponse updatedTodo(String id) {
    var savedTodo = todoRepository.findById(Integer.parseInt(id)).orElse(null);
    if (savedTodo == null)
      return null;

    return TodoResponse.builder().id(savedTodo.getId()).priority(savedTodo.getPriority())
        .status(savedTodo.getStatus()).userId(savedTodo.getUser().getId())
        .description(savedTodo.getDescription()).title(savedTodo.getTitle()).build();
    
  }

  public TodoResponse updateStatus(TodoStatusDTO todoStatusDto, String id) {

    TodoDAO todo = todoRepository.findById(Integer.parseInt(id)).orElse(null);
    if (todo == null)
      return null;
    
    todo.setStatus(todoStatusDto.getStatus());

    todoRepository.save(todo);

    return updatedTodo(id);

  }
  
  public TodoResponse updateTodo(CreateTodoRequest request, String id) {
    
    TodoDAO todo = todoRepository.findById(Integer.parseInt(id)).orElse(null);
    
    if(todo == null) return null;
    
    todo.setTitle(request.getTitle());
    todo.setDescription(request.getDescription());
    todo.setPriority(request.getPriority());
        
    todoRepository.save(todo);
    
    return updatedTodo(id);
    
  }
  
  public void deleteTodo(String id) {
    
    todoRepository.deleteById(Integer.parseInt(id));
  }

}
