package com.jello.jello_app.user.mapper;

import com.jello.jello_app.user.dto.UserDTO;
import com.jello.jello_app.user.model.User;

public class UserMapper {
    public static UserDTO toDto(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .bio(user.getBio())
                .build();
    }
}
