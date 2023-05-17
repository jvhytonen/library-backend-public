package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.category.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

 /* @Autowired
  private CategoryRepository repo;*/
  @Autowired
  private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

  @PostMapping("/")
  public Category createOne(@RequestBody CategoryDTO categoryDTO) throws Exception {
      Category newCategory = CategoryMapper.newCategory(categoryDTO);
      return categoryService.createOne(newCategory);
  }
  
  @GetMapping("/")
  public List<Category> getAll(){
    return categoryService.findAll();
  }

  @GetMapping("/{id}")
  public Category getById(@PathVariable UUID id) throws Exception {
      return categoryService.findById(id);
  }
  @DeleteMapping("/{id}")
  public void deleteOne(@PathVariable UUID id) throws Exception {
    categoryService.delete(id);
  }
  
  @PutMapping("/{id}")
  public CategoryDTO updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO category) throws Exception {
    return categoryService.update(id, category);
  }
  
}
