package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.checkout.Checkout;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookCopyMapper {
    public static BookCopyDTO newCopy(UUID copyId, Checkout latestCheckout) {
        return new BookCopyDTO(
                copyId,
                latestCheckout
        );
    }
}
