package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.book_copy.BookCopy;
import com.rest_api.fs14backend.book_copy.BookCopyService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private final BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private BookMapper bookMapper;

    @GetMapping("/")
    public List<Book> getBooks() {
        List<Book> books = bookService.getAllBooks();
        return books;
    }

    @GetMapping(value = "/{id}")
    public Book getBookById(@PathVariable UUID id) throws Exception {
        return bookService.getBookById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable UUID id) {
        Book deletedBook = bookService.deleteBook(id);
        return ResponseEntity.ok(deletedBook);
    }

    @PostMapping("/")
    public ResponseEntity<Book> createOne(@RequestBody BookDTO bookDTO) throws Exception {
        Book createdBook = bookService.createOne(bookDTO);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody BookDTO book) throws Exception {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }
}
