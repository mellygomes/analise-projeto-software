package com.jello.jello_app.user.repository;

import com.jello.jello_app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    boolean existsByUsername(String username);
}
