package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookMapper bookMapper;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createOne(BookDTO newBook) throws Exception {
        UUID categoryId = newBook.getCategoryId();
        Category category = categoryService.findById(categoryId);
        UUID authorId = newBook.getAuthorId();
        Author author = authorService.getAuthorById(authorId);
        Date yearPublished = formatPublishYear(newBook.getYearPublished());
        Book book = bookMapper.newBook(newBook, category, author, yearPublished);
        return bookRepository.save(book);
    }

    public Book getBookById(UUID id) throws Exception {
        Book bookEntity = bookRepository.findById(id).orElseThrow(() -> new Exception("No book with such id found!"));
        return bookEntity;
    }

    public Book deleteBook(UUID id) {
        Book bookToDelete = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book with such id not found"));
        bookRepository.delete(bookToDelete);
        return bookToDelete;
    }

    @Transactional
    public Book updateBook(UUID id, BookDTO updatedBook) throws Exception {
        Book bookToEdit = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("No book with such id found!"));
        Author author = authorService.getAuthorById(updatedBook.getAuthorId());
        bookToEdit.setAuthor(author);
        Category category = categoryService.findById(updatedBook.getCategoryId());
        bookToEdit.setCategory(category);
        bookToEdit.setDescription(updatedBook.getDescription());
        bookToEdit.setIsbn(updatedBook.getIsbn());
        Date yearPublished = formatPublishYear(updatedBook.getYearPublished());
        bookToEdit.setYearPublished(yearPublished);
        bookToEdit.setTitle(updatedBook.getTitle());
        bookToEdit.setPublisher(updatedBook.getPublisher());
        return bookToEdit;
    }
    public Date formatPublishYear(String year) throws ParseException {
        // We need only a year.
        SimpleDateFormat yearDate = new SimpleDateFormat("yyyy");
        yearDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        return yearDate.parse(year);
    }

}
