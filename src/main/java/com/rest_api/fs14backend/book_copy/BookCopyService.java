package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.checkout.Checkout;
import com.rest_api.fs14backend.checkout.CheckoutRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final CheckoutRepository checkoutRepository;

    public BookCopyService(BookCopyRepository bookCopyRepository, CheckoutRepository checkoutRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.checkoutRepository = checkoutRepository;
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

    public List<BookCopy> getCopyStatus(UUID id) {
        // Get all copies
        List<BookCopy> allCopies = getAllCopiesById(id);

        // Get the latest checkout of every copy
        List<Checkout> latestCheckouts = checkoutRepository.findLatestCheckouts();

        // Update the copy status with the latest checkout information
        for (BookCopy copy : allCopies) {
            Checkout latestCheckout = null;
            for (Checkout checkout : latestCheckouts) {
                if (checkout.getCopy().equals(copy)) {
                    latestCheckout = checkout;
                    break;
                }
            }
            copy.setLatestCheckout(latestCheckout);
        }

        return allCopies;
    }
}
