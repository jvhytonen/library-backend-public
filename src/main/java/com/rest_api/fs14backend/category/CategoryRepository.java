package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("SELECT a FROM Category a WHERE a.name LIKE CONCAT('%',:query, '%')")
    public Category findCategoryByQuery(String query);
}