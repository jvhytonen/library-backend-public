package com.rest_api.fs14backend.book_copy;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.rest_api.fs14backend.book.Book;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
public class BookCopy {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID copyId;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    public BookCopy(Book book) {
        this.book = book;
    }
}
