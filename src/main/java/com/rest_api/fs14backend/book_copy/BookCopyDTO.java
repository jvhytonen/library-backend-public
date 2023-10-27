package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.checkout.Checkout;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class BookCopyDTO {
    private UUID id;
    private Checkout latestCheckout;

    public BookCopyDTO(UUID copyId, Checkout latestCheckout) {
        this.id = copyId;
        this.latestCheckout = latestCheckout;
    }
}
