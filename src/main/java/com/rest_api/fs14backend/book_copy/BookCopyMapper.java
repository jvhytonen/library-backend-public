package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.book.Book;
import org.springframework.stereotype.Component;

@Component
public class BookCopyMapper {
    public static BookCopy newCopy(Book book) {
        return new BookCopy(
                book
        );
    }
}
