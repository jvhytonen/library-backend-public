package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.todo.Todo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Book createOne(Book book) {
    return bookRepository.save(book);
  }

  public Optional<Book> getBookById(UUID id) {
    return bookRepository.findById(id);
  }

  public void deleteBook(UUID id) {
    Optional<Book> bookToDelete = bookRepository.findById(id);
    if (bookToDelete.isPresent()) {
      bookRepository.delete(bookToDelete.get());
    } else {
      throw new IllegalStateException("Book with " + id + " not found");
    }
  }

  @Transactional
  public void updateBook(UUID id, Book updatedBook) {
    Optional<Book> bookToEdit = bookRepository.findById(id);
    if (bookToEdit.isPresent()) {
      bookToEdit.get().setDescription(updatedBook.getDescription());
      bookToEdit.get().setIsbn(updatedBook.getIsbn());
      bookToEdit.get().setPublishedDate(updatedBook.getPublishedDate());
      bookToEdit.get().setTitle(updatedBook.getTitle());
      bookToEdit.get().setPublishers(updatedBook.getPublishers());
    }
  }
}
