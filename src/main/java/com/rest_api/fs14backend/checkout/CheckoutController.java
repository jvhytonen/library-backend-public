package com.rest_api.fs14backend.checkout;

import com.rest_api.fs14backend.book_copy.BookCopyService;
import com.rest_api.fs14backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/")
    public List<Checkout> getAll() {
        return checkoutService.findAll();
    }

    @PostMapping("/return/")
    public ResponseEntity<Checkout> returnCopy(@RequestBody CheckoutReturnDTO borrowRequest) throws Exception {
        Checkout returnedCopy = checkoutService.returnBook(borrowRequest);
        return ResponseEntity.ok(returnedCopy);
    }

    @PostMapping("/borrow/")
    public ResponseEntity<Checkout> borrowCopy(@RequestBody CheckoutReturnDTO returnRequest) throws Exception {
        Checkout borrowedCopy = checkoutService.borrowBook(returnRequest);
        return ResponseEntity.ok(borrowedCopy);
    }
}
