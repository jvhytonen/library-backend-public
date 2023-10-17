package com.rest_api.fs14backend.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthorId(UUID authorId);
    List<Book> findByCategoryId(UUID authorId);

    @Query("SELECT b FROM Book b WHERE " +
            "b.title LIKE CONCAT('%',:query, '%')" +
            "Or b.description LIKE CONCAT('%', :query, '%')")
    List<Book> searchBooksByQuery(String query);
}