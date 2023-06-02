package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.checkout.Checkout;
import org.springframework.stereotype.Component;

@Component
public class BookCopyMapper {
    public static BookCopyDTO newCopy(BookCopy copy, Checkout latestCheckout) {
        return new BookCopyDTO(
                copy,
                latestCheckout
        );
    }
}
