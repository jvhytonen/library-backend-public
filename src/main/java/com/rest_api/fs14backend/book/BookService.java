package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createOne(Book book) {
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
        bookToEdit.setDescription(updatedBook.getDescription());
        bookToEdit.setIsbn(updatedBook.getIsbn());
        bookToEdit.setYearPublished(updatedBook.getYearPublished());
        bookToEdit.setTitle(updatedBook.getTitle());
        bookToEdit.setPublisher(updatedBook.getPublisher());
        return bookToEdit;
    }
}
