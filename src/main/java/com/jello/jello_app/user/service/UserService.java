package com.jello.jello_app.user.service;

import com.jello.jello_app.auth.dto.RegisterRequest;
import com.jello.jello_app.user.dto.UpdateUserRequest;
import com.jello.jello_app.user.dto.UserDTO;
import com.jello.jello_app.user.model.User;

public interface UserService {
    User register(RegisterRequest registerRequest);
    UserDTO userDtoBuilder(User user);
    User getUserById(Long userId);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
    User getAuthenticatedUser();
    User grantAdmin(Long userId);
    User revokeAdmin(Long userId);
    User grantModerator(Long userId);
    User revokeModerator(Long userId);
    void verifyAccountKey(String token);
    void updateLogin(String username);
}
