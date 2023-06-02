package com.rest_api.fs14backend.checkout;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class CheckoutReturnDTO {
    private UUID checkoutId;
    private UUID userId;
    private UUID copyId;
}
