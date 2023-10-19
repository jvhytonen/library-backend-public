package com.rest_api.fs14backend.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthorId(UUID authorId);
    List<Book> findByCategoryId(UUID authorId);

    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE CONCAT('%',LOWER(:query), '%')" +
            "Or LOWER(b.description) LIKE CONCAT('%', LOWER(:query), '%')" +
            "Or LOWER(b.year_published) LIKE CONCAT('%', LOWER(:query), '%')" +
            "Or LOWER(b.publisher) LIKE CONCAT('%', LOWER(:query), '%')")

    List<Book> searchBooksByQuery(String query);
}