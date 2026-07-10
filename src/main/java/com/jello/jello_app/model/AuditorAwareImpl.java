package com.jello.jello_app.model;

import com.jello.jello_app.domain.RequestContext;
import com.jello.jello_app.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Long userId = RequestContext.getUserId();

        if (userId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(userId);
    }
}
