package com.rest_api.fs14backend.checkout;

import com.rest_api.fs14backend.book_copy.BookCopy;
import com.rest_api.fs14backend.user.User;

public class CheckoutMapper {
    public Checkout newCheckout(CheckoutDTO checkoutItem, User user, BookCopy copy){
        return new Checkout(
                checkoutItem.getStartTime(),
                checkoutItem.getEndTime(),
                user, copy
        );
    }
}
