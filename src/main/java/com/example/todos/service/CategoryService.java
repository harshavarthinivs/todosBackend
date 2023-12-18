package com.example.todos.service;

import org.springframework.stereotype.Service;
import com.example.todos.model.CategoryDAO;
import com.example.todos.model.dto.CategoryResponse;
import com.example.todos.model.dto.CreateCategoryRequest;
import com.example.todos.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryResponse createCategory(CreateCategoryRequest request) {

    CategoryDAO categoryDao = CategoryDAO.builder().category(request.getCategory()).build();
    categoryRepository.save(categoryDao);

    var savedCategory = categoryRepository.findByCategory(request.getCategory());
    CategoryResponse categoryResponse = null;

    if (!savedCategory.isEmpty()) {
      CategoryDAO categoryDaoTemp = savedCategory.get();
      categoryResponse = CategoryResponse.builder().id(categoryDaoTemp.getId())
          .category(categoryDaoTemp.getCategory()).build();

    }
    return categoryResponse;
  }
  
}
