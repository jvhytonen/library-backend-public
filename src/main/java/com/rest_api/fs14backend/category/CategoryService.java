package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.author.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  public Category createOne(Category category) {
    return categoryRepository.save(category);
  }

  public Category findById(UUID categoryId) {
    return categoryRepository.findById(categoryId).orElse(null);
  }
  
  public void delete(UUID id) {
    Optional<Category> categoryToDelete = categoryRepository.findById(id);
    if (categoryToDelete.isPresent()) {
      categoryRepository.delete(categoryToDelete.get());
    } else {
      throw new IllegalStateException("Category with " + id + " not found");
    }
  }
  
  @Transactional
  public Category update(UUID id, Category newCategory) {
    Optional<Category> categoryToEdit = categoryRepository.findById(id);
    if (categoryToEdit.isPresent()) {
      categoryToEdit.get().setName(newCategory.getName());
      return newCategory;
    }
    return null;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}
