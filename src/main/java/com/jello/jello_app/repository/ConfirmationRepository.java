package com.jello.jello_app.repository;

import com.jello.jello_app.model.Confirmation;
import com.jello.jello_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Confirmation findByConfirmationKey(String key);
    Confirmation findByUser(User user);
}
