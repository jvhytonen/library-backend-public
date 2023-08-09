package com.rest_api.fs14backend.book_copy;

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
            BookCopyDTO copyWithLatestCheckout = bookCopyMapper.newCopy(copy, latestCheckout);
            copiesWithLatestCheckout.add(copyWithLatestCheckout);
        }

        return copiesWithLatestCheckout;
    }

    public BookCopy createOne(BookCopy copy) {
        return bookCopyRepository.save(copy);
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