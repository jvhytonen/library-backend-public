package com.rest_api.fs14backend.checkout;

import com.rest_api.fs14backend.book_copy.BookCopy;
import com.rest_api.fs14backend.book_copy.BookCopyService;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserRepository;
import com.rest_api.fs14backend.exceptions.CustomException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CheckoutService {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookCopyService bookCopyService;

    public Checkout borrowBook(CheckoutReturnDTO checkoutReturnDTO) throws Exception {
        if (checkCopyAvailability(checkoutReturnDTO.getCopyId()) && checkIfUserExists(checkoutReturnDTO.getUserId())) {
            User user = userRepository.findById(checkoutReturnDTO.getUserId()).orElseThrow(() -> new CustomException("No user with such id found!"));
            BookCopy copy = bookCopyService.getCopyById(checkoutReturnDTO.getCopyId());
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
                throw new CustomException("The copy is already borrowed!");
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
        throw new CustomException("User with such user id do not exist!");
    }

    public Date createEndTime(Date startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date endDate = calendar.getTime();
        return endDate;
    }

    @Transactional
    public Checkout returnBook(CheckoutReturnDTO checkoutReturnDTO) throws Exception {
        Checkout checkoutToReturn = checkoutRepository.findById(checkoutReturnDTO.getCheckoutId()).orElseThrow(() -> new CustomException("No checkout with such id found!"));
        if (checkoutReturnDTO.getUserId().equals(checkoutToReturn.getUser().getId())) {
            checkoutToReturn.setReturned(true);
            return checkoutToReturn;
        } else {
            throw new CustomException("Book is not borrowed by the current user!");
        }
    }

    public List<Checkout> findAll() {
        return checkoutRepository.findAll();
    }
}
