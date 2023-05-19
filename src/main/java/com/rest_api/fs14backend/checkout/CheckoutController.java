package com.rest_api.fs14backend.checkout;

import com.rest_api.fs14backend.book_copy.BookCopy;
import com.rest_api.fs14backend.book_copy.BookCopyService;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RestController
@RequestMapping("api/v1/checkouts")
@RequiredArgsConstructor
public class CheckoutController {
    @Autowired
    private final CheckoutService checkoutService;

    @Autowired
    private UserService userService;
    @Autowired
    private BookCopyService bookCopyService;

    private CheckoutMapper checkoutMapper;

    @PutMapping("/return")
    public Checkout returnBook(@RequestBody CheckoutDTO checkoutDTO) throws Exception {
        return checkoutService.returnBook(checkoutDTO);
    }

    @PostMapping("/")
    public Checkout createOne(@RequestBody CheckoutDTO checkoutDTO) throws Exception {
        return checkoutService.borrowBook(checkoutDTO);
    }
}
