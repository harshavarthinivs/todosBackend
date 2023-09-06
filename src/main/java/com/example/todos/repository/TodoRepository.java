package com.example.todos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todos.model.TodoDAO;
import com.example.todos.user.User;

public interface TodoRepository extends JpaRepository<TodoDAO , Integer> {
  
  void deleteById(Integer id);
  
  Optional<TodoDAO> findById(Integer id);
  
  Optional<TodoDAO> findTopByUserOrderByIdDesc(User user);
}
