package com.jello.jello_app.confirmation.repository;

import com.jello.jello_app.confirmation.model.Confirmation;
import com.jello.jello_app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Optional<Confirmation> findByConfirmationKey(String key);
    Optional<Confirmation> findByUser(User user);
}
