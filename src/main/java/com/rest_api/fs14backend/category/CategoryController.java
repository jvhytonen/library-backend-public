package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.category.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @CrossOrigin(origins = "http://127.0.0.1:5173/", allowedHeaders = {"Authorization", "Content-Type"})
    @PostMapping("/")
    public ResponseEntity<Category> createOne(@RequestBody CategoryDTO categoryDTO) throws Exception {
        Category newCategory = categoryMapper.newCategory(categoryDTO);
        Category createdCategory = categoryService.createOne(newCategory);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/")
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable UUID id) throws Exception {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteOne(@PathVariable UUID id) throws Exception {
        Category deletedCategory = categoryService.delete(id);
        return ResponseEntity.ok(deletedCategory);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO category) throws Exception {
        return categoryService.update(id, category);
    }

}
