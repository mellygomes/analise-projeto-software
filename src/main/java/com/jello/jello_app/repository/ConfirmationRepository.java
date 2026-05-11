package com.jello.jello_app.repository;

import com.jello.jello_app.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
}
