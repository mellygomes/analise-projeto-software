package com.jello.jello_app.service;

import com.jello.jello_app.dto.*;
import com.jello.jello_app.model.User;

public interface UserService {
    User register(RegisterRequest registerRequest);
    UserDTO userDtoBuilder(User user);
    User getUserById(Long userId);
}
