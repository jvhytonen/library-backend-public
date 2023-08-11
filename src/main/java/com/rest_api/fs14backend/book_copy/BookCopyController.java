package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RestController
@RequestMapping("api/v1/book-copies")
@RequiredArgsConstructor
public class BookCopyController {
    @Autowired
    private BookCopyService bookCopyService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookCopyCreationDTO bookCopyCreationDTO;

    @GetMapping("/")
    public List<BookCopy> getBookCopies() {
        return bookCopyService.getAllBookCopies();
    }

    @GetMapping("/{id}")
    public List<BookCopyDTO> getCopiesByBookId(@PathVariable UUID id) throws Exception {
        return bookCopyService.getCopiesByBookId(id);
    }

    @PostMapping("/")
    public ResponseEntity<BookCopyDTO> createOne(@RequestBody BookCopyCreationDTO newCopy) throws Exception {
       BookCopyDTO createdCopy = bookCopyService.createCopy(newCopy);
        return ResponseEntity.ok(createdCopy);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteCopy(@PathVariable UUID id) throws Exception {
        BookCopy deletedCopy = bookCopyService.deleteCopy(id);
        return ResponseEntity.ok(id);
    }
}
