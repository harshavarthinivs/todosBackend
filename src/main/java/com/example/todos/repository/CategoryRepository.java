package com.example.todos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todos.model.CategoryDAO;

public interface CategoryRepository extends JpaRepository<CategoryDAO , Integer>{
  
  void deleteById(Integer id);
  
  Optional<CategoryDAO> findById(Integer id);
  
  Optional<CategoryDAO> findByCategory(String category);
 
}
