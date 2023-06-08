package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BookMapper {
    public Book newBook(BookDTO book, Category category, Author author, Date yearPublished) {
        return new Book(
                book.getIsbn(),
                book.getTitle(),
                yearPublished,
                book.getImageUrl(),
                book.getDescription(),
                book.getPublisher(),
                category, author);
    }

}
