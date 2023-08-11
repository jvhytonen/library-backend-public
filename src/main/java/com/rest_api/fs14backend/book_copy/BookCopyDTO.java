package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.checkout.Checkout;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class BookCopyDTO {
    private UUID bookCopyId;
    private Checkout latestCheckout;

    public BookCopyDTO(UUID copyId, Checkout latestCheckout) {
        this.bookCopyId = copyId;
        this.latestCheckout = latestCheckout;
    }
}
