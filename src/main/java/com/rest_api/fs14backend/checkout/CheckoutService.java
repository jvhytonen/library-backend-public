package com.rest_api.fs14backend.checkout;

import com.rest_api.fs14backend.book_copy.BookCopy;
import com.rest_api.fs14backend.book_copy.BookCopyService;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;

    public CheckoutService(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookCopyService bookCopyService;

    public Checkout borrowBook(CheckoutDTO checkoutDTO) throws Exception {
        if (checkCopyAvailability(checkoutDTO.getCopyId()) && checkIfUserExists(checkoutDTO.getUserId())) {
            User user = userRepository.findById(checkoutDTO.getUserId()).orElse(null);
            BookCopy copy = bookCopyService.getCopyById(checkoutDTO.getCopyId());
            Date startTime = new Date();
            Date endTime = createEndTime(startTime);
            Checkout checkout = new Checkout(false, startTime, endTime, user, copy);
            return checkoutRepository.save(checkout);
        } else {
            throw new IllegalStateException("An error occurred!");
        }
    }

    public boolean checkCopyAvailability(UUID copyId) {
        List<Checkout> allCheckouts = checkoutRepository.findAll();
        for (Checkout checkout : allCheckouts) {
            if (copyId.equals(checkout.getCopy()) && !checkout.isReturned()) {
                throw new IllegalStateException("The copy is already borrowed!");
            }
        }
        return true;
    }
    public boolean checkIfUserExists(UUID userId) {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if (userId.equals(user.getId())) {
                return true;
            }
        }
        // If the loop goes through all the users, there is no user with the right id.
            throw new IllegalStateException("User with such user id do not exist!");
    }

    public Date createEndTime(Date startTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date endDate = calendar.getTime();
        return endDate;
    }

    public Checkout returnBook(CheckoutDTO checkoutDTO) throws Exception {
        Checkout bookToBeReturned = findBookToBeReturned(checkoutDTO.getCopyId());
        if (checkoutDTO.getUserId().equals(bookToBeReturned.getUser().getId())) {
            bookToBeReturned.setReturned(true);
            return bookToBeReturned;
        } else {
            throw new IllegalStateException("Book is not borrowed by the current user!");
        }
    }
    public Checkout findBookToBeReturned(UUID copyId) {
        List<Checkout> allCheckouts = checkoutRepository.findAll();
        for (Checkout checkout : allCheckouts) {
            if (copyId.equals(checkout.getCopy().getCopyId())) {
                return checkout;
            }
        }
        throw new IllegalStateException("No borrowed book with such id found!");
    }
    public List<Checkout> findAll() {
        return checkoutRepository.findAll();
    }
}
