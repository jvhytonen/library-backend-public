package com.rest_api.fs14backend.book_copy;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class BookCopyDTO {
    private UUID bookId;
}
