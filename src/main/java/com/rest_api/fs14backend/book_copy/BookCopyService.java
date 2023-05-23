package com.rest_api.fs14backend.book_copy;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;

    public BookCopyService(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    public List<BookCopy> getAllCopiesById(UUID bookId) {
        List<BookCopy> allCopies = bookCopyRepository.findAll();
        List<BookCopy> copiesById = new ArrayList<>();
        for (BookCopy copy : allCopies) {
            if (bookId.equals(copy.getBook().getId())){
                copiesById.add(copy);
            }
        }
        return copiesById;
    }
    public BookCopy createOne(BookCopy copy) {
        return bookCopyRepository.save(copy);
    }

    public void deleteCopy(UUID id) throws Exception {
        BookCopy copyToDelete = bookCopyRepository.findById(id).orElseThrow(() -> new Exception("No copy with such id found!"));
        bookCopyRepository.delete(copyToDelete);
    }

    public BookCopy getCopyById(UUID id) throws Exception {
        return bookCopyRepository.findById(id).orElseThrow(() -> new Exception("No copy with such id found"));
    }
}
