package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.author.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

 /* @Autowired
  private CategoryRepository repo;*/
  @Autowired
  private CategoryService categoryService;

  @PostMapping("/")
  public Category createOne(@RequestBody Category category) {
    return categoryService.createOne(category);
  }
  
  @GetMapping("/")
  public List<Category> getAll(){
    return categoryService.findAll();
  }

  @GetMapping("/{id}")
  public Category getById(@PathVariable UUID id) {
      return categoryService.findById(id);
  }
  @DeleteMapping("/{id}")
  public void deleteOne(@PathVariable UUID id) {
    categoryService.delete(id);
  }
  
  @PutMapping("/{id}")
  public Category updateBook(@PathVariable UUID id, @RequestBody Category category) {
    return categoryService.update(id, category);
  }
  
}
