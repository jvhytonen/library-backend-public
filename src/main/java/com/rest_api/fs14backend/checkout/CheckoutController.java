package com.rest_api.fs14backend.checkout;

import com.rest_api.fs14backend.book_copy.BookCopyService;
import com.rest_api.fs14backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"https://stately-starship-e19365.netlify.app/", "http://127.0.0.1:5173/"}, allowedHeaders = {"Authorization", "Content-Type"})
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
    public ResponseEntity<List<Checkout>> getAll() {
        List<Checkout> checkouts = checkoutService.findAll();
        return ResponseEntity.ok(checkouts);
    }

    @PostMapping("/return/")
    public ResponseEntity<Checkout> returnCopy(@RequestBody CheckoutReturnDTO returnRequest) throws Exception {
        Checkout returnedCopy = checkoutService.returnBook(returnRequest);
        return ResponseEntity.ok(returnedCopy);
    }

    @PostMapping("/borrow/")
    public ResponseEntity<Checkout> borrowCopy(@RequestBody CheckoutBorrowDTO borrowRequest) throws Exception {
        Checkout borrowedCopy = checkoutService.borrowBook(borrowRequest);
        return ResponseEntity.ok(borrowedCopy);
    }
}
