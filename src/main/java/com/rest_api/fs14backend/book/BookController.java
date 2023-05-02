package com.rest_api.fs14backend.book;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
  private final BookService bookService;

  public BookController( BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public List<Book> getBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping(value = "/{isbn}")
  public Optional<Book> getBookByIsbn(@PathVariable Long isbn) {
    return bookService.getBookById(isbn);
  }

  @DeleteMapping(value = "/{isbn}")
  public void deleteBook(@PathVariable Long isbn) {
    bookService.deleteBook(isbn);
  }

  @PostMapping
  public void createOne(@RequestBody Book book) {
    bookService.addOneBook(book);
  }
  @PutMapping(value = "/{isbn}")
  public void updateBook(@PathVariable Long isbn, @RequestBody Book book) {
    bookService.updateBook(isbn, book);
  }
}
