package com.rest_api.fs14backend.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("SELECT a FROM Category a WHERE LOWER(a.name) LIKE CONCAT('%',LOWER(:query), '%')")
    public List<Category> findCategoryByQuery(String query);

    Optional<Category> findByName(String name);
}