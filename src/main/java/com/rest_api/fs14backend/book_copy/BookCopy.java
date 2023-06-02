package com.rest_api.fs14backend.book_copy;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.checkout.Checkout;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
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

    @JsonManagedReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "copy")
    private List<Checkout> checkouts;

    public BookCopy(Book book) {
        this.book = book;
    }
}