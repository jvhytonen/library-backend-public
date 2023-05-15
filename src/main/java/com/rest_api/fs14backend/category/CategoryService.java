package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private final BookRepository bookRepository;

  public CategoryService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Category createOne(Category category) throws Exception{
    if (category == null) {
      throw new IllegalStateException("Data cannot be null!");
    }
    return categoryRepository.save(category);
  }

  public Category findById(UUID categoryId) throws Exception {
    Category categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new Exception("No category with such id found!"));
    if (categoryEntity == null) {
      throw new Exception("No category with such id found!");
    }
    return categoryEntity;
  }

  public void delete(UUID id) throws Exception {
    Book bookEntity = bookRepository.findAll()
            .stream()
            .filter(
                    pro -> Objects.equals(pro.getCategory().getId(), id))
            .findFirst()
            .orElse(null);
    if (null != bookEntity) {
     throw new Exception("Book that has this category still exists!");
    }  else {
      categoryRepository.deleteById(id);
    }
  }

  @Transactional
  public CategoryDTO update(UUID id, CategoryDTO newCategory) throws Exception {
   Category categoryToEdit = categoryRepository.findById(id).orElseThrow(() -> new Exception("No category with such id found!"));
      categoryToEdit.setName(newCategory.getName());
      return newCategory;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}
