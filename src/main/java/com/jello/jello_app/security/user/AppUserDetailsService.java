package com.jello.jello_app.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService {
    private final UserRepository userRepository;

}
