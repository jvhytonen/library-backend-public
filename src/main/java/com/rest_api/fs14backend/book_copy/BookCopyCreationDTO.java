package com.rest_api.fs14backend.book_copy;

import com.rest_api.fs14backend.checkout.Checkout;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@NoArgsConstructor
@Data
@Component
public class BookCopyCreationDTO {
    private UUID bookId;

    public BookCopyCreationDTO(BookCopy copy) {
        this.bookId = bookId;
    }
}