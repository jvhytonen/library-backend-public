package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column
    private String isbn;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private Date yearPublished;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String publisher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    public Book(String isbn,
                String title,
                Date yearPublished,
                String imageUrl,
                String description,
                String publisher,
                Category category,
                Author author
    ) {
        this.isbn = isbn;
        this.title = title;
        this.yearPublished = yearPublished;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publisher = publisher;
        this.category = category;
        this.author = author;
    }
}


