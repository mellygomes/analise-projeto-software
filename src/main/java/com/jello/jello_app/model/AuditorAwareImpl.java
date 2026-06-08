package com.jello.jello_app.model;

import com.jello.jello_app.domain.RequestContext;
import com.jello.jello_app.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getCurrentAuditor() {
        Long userId = RequestContext.getUserId();

        if (userId == null) {
            return Optional.empty();
        }

        return Optional.of(entityManager.getReference(User.class, userId));
    }
}
