package com.jello.jello_app.user.service;

import com.jello.jello_app.auth.dto.RegisterRequest;
import com.jello.jello_app.user.dto.UpdateUserRequest;
import com.jello.jello_app.user.model.User;

public interface UserService {
    User register(RegisterRequest registerRequest);

    User getUserById(Long userId);

    User updateUser(UpdateUserRequest request, Long userId);

    void deleteUser(Long userId);
}
