package com.example.todos.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.example.todos.enums.TodoStatus;
import com.example.todos.model.CategoryDAO;
import com.example.todos.model.TodoDAO;
import com.example.todos.model.dto.CreateTodoRequest;
import com.example.todos.model.dto.TodoResponse;
import com.example.todos.model.dto.TodoStatusDTO;
import com.example.todos.model.dto.TodoStatusWithIdDTO;
import com.example.todos.repository.CategoryRepository;
import com.example.todos.repository.TodoRepository;
import com.example.todos.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;


  public TodoResponse createTodo(CreateTodoRequest request) {

    var user = userRepository.findById(request.getUserid());
    
    /*
     * id = 1 means 'none' is the default category 
     * */
    int id = 1;
    if (request.getCategoryid() != null)
      id = request.getCategoryid();
    var category = categoryRepository.findById(id);

    if (!user.isPresent())
      return null;

    if (!category.isPresent()) {
      return null;
    }

    var todo = TodoDAO.builder().title(request.getTitle()).description(request.getDescription())
        .priority(request.getPriority()).status(request.getStatus()).user(user.get())
        .categoryDao(category.get()).build();

    todoRepository.save(todo);

    var savedTodo = todoRepository.findTopByUserOrderByIdDesc(todo.getUser());
    if (savedTodo.isPresent()) {
      var todoObject = savedTodo.get();
      return TodoResponse.builder().id(todoObject.getId()).priority(todoObject.getPriority())
          .status(todoObject.getStatus()).userId(todoObject.getUser().getId())
          .title(todoObject.getTitle()).description(todoObject.getDescription())
          .categoryId(todoObject.getCategoryDao().getId()).build();
    }
    return null;
  }

  /**
   * Helper function used for returning TodoResponse After updation
   */
  public TodoResponse updatedTodo(String id) {
    var savedTodo = todoRepository.findById(Integer.parseInt(id)).orElse(null);
    if (savedTodo == null)
      return null;

    return TodoResponse.builder().id(savedTodo.getId()).priority(savedTodo.getPriority())
        .status(savedTodo.getStatus()).userId(savedTodo.getUser().getId())
        .categoryId(savedTodo.getCategoryDao().getId()).description(savedTodo.getDescription())
        .title(savedTodo.getTitle()).build();

  }

  public TodoResponse updateStatus(TodoStatusDTO todoStatusDto, String id) {

    TodoDAO todo = todoRepository.findById(Integer.parseInt(id)).orElse(null);
    if (todo == null)
      return null;

    todo.setStatus(todoStatusDto.getStatus());

    todoRepository.save(todo);

    return updatedTodo(id);

  }
  
  public Object updateStatus(List<TodoStatusWithIdDTO> request) {
    
    List<TodoResponse> toReturn = new ArrayList<TodoResponse>();
    for(var current:request) {
      
      var returned = updateStatus(current.getStatus(),current.getId());
      toReturn.add(returned);
    }
    
    return toReturn;
  }

  public TodoResponse updateTodo(CreateTodoRequest request, String id) {

    TodoDAO todo = todoRepository.findById(Integer.parseInt(id)).orElse(null);
    
    int _id = 1;
    if (request.getCategoryid() != null)
      _id = request.getCategoryid();
    
    var categoryOpt = categoryRepository.findById(_id);

    CategoryDAO category = null;
    if (!categoryOpt.isPresent()) {
      var temp = categoryRepository.findById(1);
      if (temp.isPresent())
        category = temp.get();
    } else
      category = categoryOpt.get();

    if (todo == null)
      return null;

    todo.setTitle(request.getTitle());
    todo.setDescription(request.getDescription());
    todo.setPriority(request.getPriority());
    todo.setCategoryDao(category);

    todoRepository.save(todo);

    return updatedTodo(id);

  }

  public void deleteTodo(String id) {
    todoRepository.deleteById(Integer.parseInt(id));
  }

  public void deleteTodo(List<String> ids) {
    for(var id : ids) deleteTodo(id);
  }



}
