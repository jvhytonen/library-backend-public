package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
public class Book {

  @Id
  @Column(name = "id")
  @SequenceGenerator(
          name = "book_sequence",
          sequenceName = "book_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "book_sequence"
  )
  private long id;

  @Column( unique = true)
  private long ISBN;

  @Column(nullable = true)
  private String title;

  @Column(nullable = true)
  private LocalDate publishedDate;

  @Column(nullable = true)
  private String description;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(nullable = true)
  private String publishers;

  @ManyToOne(optional = false)
  @JoinColumn(name = "author_id")
  private Author author;

  enum Status {
    BORROWED,
    AVAILABLE
  }
  
  @ManyToOne( optional = false)
  @JoinColumn(name = "category_id")
  private Category category;

  public Book(long ISBN,
              String title,
              LocalDate publishedDate,
              String description,
              Status status,
              String publishers,
              Category category,
              Author author
  ) {
    this.ISBN = ISBN;
    this.title = title;
    this.publishedDate = publishedDate;
    this.description = description;
    this.status = status;
    this.publishers = publishers;
    this.category = category;
    this.author = author;
  
  }
  public Book (long ISBN, String title) {
    this.ISBN = ISBN;
    this.title = title;
  }
}


