package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RestController
@RequestMapping("api/v1/book-copies")
@RequiredArgsConstructor
public class BookCopyController {
    private final BookService bookService;
    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping("/")
    public List<BookCopy> getBookCopies() {
        return bookCopyService.getAllBookCopies();
    }

    @PostMapping("/")
    public BookCopy createOne(@RequestBody BookCopyDTO bookCopyDTO) throws Exception {
        UUID bookId = bookCopyDTO.getBookId();
        Book book = bookService.getBookById(bookId);
        BookCopy copy = new BookCopy(book);
        return bookCopyService.createOne(copy);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteBook(@PathVariable UUID id) throws Exception {
        bookCopyService.deleteCopy(id);
    }
}
