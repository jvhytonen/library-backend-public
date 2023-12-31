package com.rest_api.fs14backend.author;

import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryMapper;
import com.rest_api.fs14backend.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = {"https://stately-starship-e19365.netlify.app/", "http://127.0.0.1:5173/"}, allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository repo;

    @GetMapping("/")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable UUID id) throws Exception {
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping("/")
    public ResponseEntity<Author> addOne(@RequestBody Author author) {
        Author newAuthor = AuthorMapper.newAuthor(author);
        authorService.createOne(newAuthor);
        return ResponseEntity.ok(newAuthor);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Author> delete(@PathVariable UUID id) throws Exception {
        try {
            Author deletedAuthor = authorService.delete(id);
            return ResponseEntity.ok(deletedAuthor);
        }
        catch (CustomException ex) {
            // Re-throw the CustomException
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable UUID id, @RequestBody AuthorDTO author) throws Exception {
        Author updatedAuthor = authorService.update(id, author);
        return ResponseEntity.ok(updatedAuthor);
    }
}
