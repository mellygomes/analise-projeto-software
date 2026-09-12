package com.jello.jello_app.common.model;

import com.jello.jello_app.domain.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
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
