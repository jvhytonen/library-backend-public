package com.rest_api.fs14backend.author;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @Query("SELECT s FROM Author s WHERE s.id = ?1")
    public Author findAuthorById(UUID id);
    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE CONCAT('%', LOWER(:query), '%')")
    public List<Author> findAuthorByQuery(String query);
}

