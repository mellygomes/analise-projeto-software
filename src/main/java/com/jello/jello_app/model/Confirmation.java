package com.jello.jello_app.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "confirmations")
@Getter
@Setter
@NoArgsConstructor
public class Confirmation extends Auditable {

    @Column(nullable = false, unique = true)
    private String confirmationKey;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Confirmation(User user) {
        this.user = user;
        this.confirmationKey = UUID.randomUUID().toString();
    }
}