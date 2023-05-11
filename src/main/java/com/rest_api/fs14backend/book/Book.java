package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
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
  private LocalDate publishedDate;

  @Column(nullable = true)
  private String description;

  @Column(nullable = true)
  private String publishers;

  @ManyToOne(optional = false)
/*  @JoinColumn(name = "author_id") */
  private Author author;
  
  @ManyToOne( optional = false)
  /*@JoinColumn(name = "category_id")*/
  private Category category;

  public Book(String isbn,
              String title,
              LocalDate publishedDate,
              String description,
              String publishers,
              Category category,
              Author author
  ) {
    this.isbn = isbn;
    this.title = title;
    this.publishedDate = publishedDate;
    this.description = description;
    this.publishers = publishers;
    this.category = category;
    this.author = author;
  
  }
}


