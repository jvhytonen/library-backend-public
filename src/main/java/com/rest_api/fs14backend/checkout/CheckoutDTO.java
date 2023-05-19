package com.rest_api.fs14backend.checkout;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class CheckoutDTO {
    private UUID userId;
    private UUID bookId;
    private Date startTime;
    private Date endTime;
    private boolean isBorrowed;
}
