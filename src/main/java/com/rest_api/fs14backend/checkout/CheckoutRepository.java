package com.rest_api.fs14backend.checkout;

import com.rest_api.fs14backend.book_copy.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CheckoutRepository extends JpaRepository<Checkout, UUID> {
    @Query("SELECT c FROM Checkout c WHERE c.copy = :copy ORDER BY c.startTime DESC LIMIT 1")
    Checkout findLatestCheckout(@Param("copy") BookCopy copy);
}