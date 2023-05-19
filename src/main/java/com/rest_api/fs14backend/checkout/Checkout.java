package com.rest_api.fs14backend.checkout;

import com.rest_api.fs14backend.book_copy.BookCopy;
import com.rest_api.fs14backend.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
public class Checkout {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private Date startTime;
    private Date endTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "copy_id")
    private BookCopy copy;
    @Column(nullable = false)
    private boolean isBorrowed;

    public Checkout(Date startTime, Date endTime, User user, BookCopy copy) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.copy = copy;

    }
}
