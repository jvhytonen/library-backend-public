package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.checkout.Checkout;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class BookCopyDTO {
    private BookCopy bookCopy;
    private Checkout latestCheckout;

    public BookCopyDTO(BookCopy copy, Checkout latestCheckout) {
        this.bookCopy = copy;
        this.latestCheckout = latestCheckout;
    }
}
