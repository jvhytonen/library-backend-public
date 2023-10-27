package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookRepository;
import com.rest_api.fs14backend.exceptions.CustomException;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

  public Category createOne(Category category) throws Exception {
    if (category == null) {
      throw new IllegalStateException("Data cannot be null!");
    }
    //Check if there already exists a category with that name.
    Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
    if (existingCategory.isPresent()) {
      throw new CustomException("Category with the same name already exists");
    }
    return categoryRepository.save(category);
  }

  public Category findById(UUID categoryId) throws Exception {
    Category categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new CustomException("No category with such id found!"));
    if (categoryEntity == null) {
      throw new Exception("No category with such id found!");
    }
    return categoryEntity;
  }

  public Category delete(UUID id) throws NotFoundException, Exception {
    // This will check if the category to be deleted is still a foreign key in some book.
    Book bookEntity = bookRepository.findAll()
            .stream()
            .filter(
                    pro -> Objects.equals(pro.getCategory().getId(), id))
            .findFirst()
            .orElse(null);
    // If the category is still related to a book, it cannot be removed.
    if (null != bookEntity) {
      throw new CustomException("Cannot remove a category that is used by at least one book");
    } else {
      Category deletedCategory = categoryRepository.findById(id).orElseThrow(() -> new CustomException("No category with such id found"));
      categoryRepository.delete(deletedCategory);
      return deletedCategory;
    }
  }

  @Transactional
  public CategoryDTO update(UUID id, CategoryDTO newCategory) throws Exception {
    Category categoryToEdit = categoryRepository.findById(id).orElseThrow(() -> new CustomException("No category with such id found!"));
    categoryToEdit.setName(newCategory.getName());
    return newCategory;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public List<Category> queryByString(String query) {
    return categoryRepository.findCategoryByQuery(query);
  }
}
