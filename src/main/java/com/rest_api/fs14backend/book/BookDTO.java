package com.rest_api.fs14backend.book;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
public class BookDTO {
    private UUID categoryId;
    private UUID authorId;
    private String isbn;
    private String title;
    private int yearPublished;
    private String imageUrl;
    private String description;
    private String publisher;

}
