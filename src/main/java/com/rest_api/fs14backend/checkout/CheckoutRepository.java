package com.rest_api.fs14backend.checkout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CheckoutRepository extends JpaRepository<Checkout, UUID> {
    @Query(value = "SELECT * FROM ( " +
            "   SELECT *, ROW_NUMBER() OVER (PARTITION BY copy_id ORDER BY start_time DESC) as rn " +
            "   FROM Checkout" +
            ") AS ranked " +
            "WHERE rn = 1",
            nativeQuery = true)
    List<Checkout> findLatestCheckouts();
}
