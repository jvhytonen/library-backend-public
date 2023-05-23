package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.book_copy.BookCopy;
import com.rest_api.fs14backend.book_copy.BookCopyService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "http://127.0.0.1:5173/")
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
  private BookCopyService bookCopyService;
  
  @Autowired
  private BookMapper bookMapper;

  @GetMapping("/")
  public List<Book> getBooks() {
    List<Book> books = bookService.getAllBooks();
    for (Book book : books) {
      List<BookCopy> copies = bookCopyService.getAllCopiesById(book.getId());
      book.setCopies(copies);
    }
    return books;
  }

  @GetMapping(value = "/{id}")
  public Book getBookById(@PathVariable UUID id) throws Exception {
    return bookService.getBookById(id);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteBook(@PathVariable UUID id) {
    bookService.deleteBook(id);
  }

  @PostMapping("/")
  public Book createOne(@RequestBody BookDTO bookDTO) throws Exception {
    UUID categoryId = bookDTO.getCategoryId();
    Category category = categoryService.findById(categoryId);
    UUID authorId = bookDTO.getAuthorId();
    Author author = authorService.getAuthorById(authorId);
    Book book = bookMapper.newBook(bookDTO, category, author);
    return bookService.createOne(book);
  }

  @PutMapping(value = "/{id}")
  public void updateBook(@PathVariable UUID id, @RequestBody BookDTO book) throws Exception {
    bookService.updateBook(id, book);
  }
}
