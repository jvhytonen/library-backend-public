package com.rest_api.fs14backend.author;


import com.rest_api.fs14backend.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
  @Autowired
  private AuthorService authorService;
  @Autowired
  private AuthorRepository repo;
  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping("/")
  public List<Author> getAllAuthors(){
    return authorService.findAll();
  }
  @GetMapping("/{id}")
  public Author findById(@PathVariable  UUID id) {
    return authorService.getAuthorById(id);
  }
  @PostMapping("/")
  public Author addOne(@RequestBody Author author) {
    return authorService.createOne(author);
  }
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable UUID id) {
    authorService.delete(id);
  }
  @PutMapping("/{id}")
  public Author update(@PathVariable UUID id, @RequestBody Author author) {
    return authorService.update(id, author);
  }
}
