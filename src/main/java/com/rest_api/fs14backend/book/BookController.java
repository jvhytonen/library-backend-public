package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;
  
  @Autowired
  private CategoryService categoryService;

  @Autowired
  private AuthorService authorService;
  
  @Autowired
  private BookMapper bookMapper;

  @GetMapping("/")
  public List<Book> getBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping(value = "/{id}")
  public Optional<Book> getBookById(@PathVariable UUID id) {
    return bookService.getBookById(id);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteBook(@PathVariable UUID id) {
    bookService.deleteBook(id);
  }

  @PostMapping("/")
  public Book createOne(@RequestBody BookDTO bookDTO) {
    UUID categoryId = bookDTO.getCategoryId();
    Category category = categoryService.findById(categoryId);
    UUID authorId = bookDTO.getAuthorId();
    Author author = authorService.getAuthorById(authorId);
    Book book = bookMapper.newBook(bookDTO, category, author);
    return bookService.createOne(book);
  }

  @PutMapping(value = "/{id}")
  public void updateBook(@PathVariable UUID id, @RequestBody Book book) {
    bookService.updateBook(id, book);
  }
}
