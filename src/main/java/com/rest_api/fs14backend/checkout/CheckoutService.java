package com.rest_api.fs14backend.checkout;

import com.rest_api.fs14backend.book_copy.BookCopy;
import com.rest_api.fs14backend.book_copy.BookCopyService;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;

    public CheckoutService(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    private UserService userService;

    @Autowired
    private BookCopyService bookCopyService;

    private CheckoutMapper checkoutMapper;
    public Checkout borrowBook(CheckoutDTO checkoutDTO) throws Exception {
        if(checkCopyAvailability(checkoutDTO.getBookId())) {
            UUID userId = checkoutDTO.getUserId();
            User user = userService.findById(userId);
            UUID bookId = checkoutDTO.getBookId();
            BookCopy copy = bookCopyService.getCopyById(bookId);
            Checkout checkout = checkoutMapper.newCheckout(checkoutDTO, user, copy);
            return checkoutRepository.save(checkout);
        }
        else {
            throw new IllegalStateException("An error occurred!");
        }
    }
    public boolean checkCopyAvailability(UUID copyId){
        List<Checkout> allCheckouts = checkoutRepository.findAll();
        for (Checkout checkout : allCheckouts) {
            if(copyId.equals(checkout.getCopy()) && checkout.isBorrowed()){
                throw new IllegalStateException("The copy is already borrowed!");
            }
        }
        return true;
    }
    public Checkout returnBook(CheckoutDTO checkoutDTO) throws Exception {
        Checkout bookToBeReturned = checkoutRepository.findById(checkoutDTO.getBookId()).orElseThrow(() -> new Exception("No copy with such id found!"));
        if (checkoutDTO.getUserId().equals(bookToBeReturned.getUser())) {
            bookToBeReturned.setBorrowed(false);
            return bookToBeReturned;
        }
        else {
            throw new IllegalStateException("Book is not borrowed by the current user!");
        }
    }
}
