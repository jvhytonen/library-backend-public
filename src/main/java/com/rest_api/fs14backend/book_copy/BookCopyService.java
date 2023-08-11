package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookService;
import com.rest_api.fs14backend.checkout.Checkout;
import com.rest_api.fs14backend.checkout.CheckoutRepository;
import com.rest_api.fs14backend.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    BookCopyMapper bookCopyMapper;

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    public List<BookCopy> findAllByBookId(UUID bookId) {
        return bookCopyRepository.findAllByBookId(bookId);
    }

    public List<BookCopyDTO> getCopiesByBookId(UUID bookId) {
        List<BookCopy> allCopies = findAllByBookId(bookId);
        List<BookCopyDTO> copiesWithLatestCheckout = new ArrayList<>();
        for (BookCopy copy : allCopies) {
            Checkout latestCheckout = checkoutRepository.findLatestCheckout(copy);
            BookCopyDTO copyWithLatestCheckout = bookCopyMapper.newCopy(copy.getCopyId(), latestCheckout);
            copiesWithLatestCheckout.add(copyWithLatestCheckout);
        }

        return copiesWithLatestCheckout;
    }

    public BookCopyDTO createCopy(BookCopyCreationDTO newCopy) throws Exception {
        //Getting the book by ID.
        UUID bookId = newCopy.getBookId();
        Book book = bookService.getBookById(bookId);
        // Creating a copy
        BookCopy copy = bookCopyRepository.save(new BookCopy(book));
        // Creating response object
        BookCopyDTO copyResponse = bookCopyMapper.newCopy(copy.getCopyId(), null);
        return copyResponse;
    }

    public BookCopy deleteCopy(UUID id) throws Exception {
        BookCopy copyToDelete = bookCopyRepository.findById(id).orElseThrow(() -> new CustomException("No copy with such id found!"));
        bookCopyRepository.delete(copyToDelete);
        return copyToDelete;
    }

    public BookCopy getCopyById(UUID id) throws Exception {
        return bookCopyRepository.findById(id).orElseThrow(() -> new CustomException("No copy with such id found"));
    }
}