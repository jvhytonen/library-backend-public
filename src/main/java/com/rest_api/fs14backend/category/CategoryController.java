package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.category.CategoryMapper;
import com.rest_api.fs14backend.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "https://stately-starship-e19365.netlify.app/")
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
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable UUID id) throws Exception {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteOne(@PathVariable UUID id) throws Exception {
        try {
            Category deletedCategory = categoryService.delete(id);
            return ResponseEntity.ok(deletedCategory);
        } catch (CustomException ex) {
            // Re-throw the CustomException
            throw ex;
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO category) throws Exception {
        CategoryDTO updatedCategory = categoryService.update(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

}
