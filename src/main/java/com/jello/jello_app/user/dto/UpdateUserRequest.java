package com.jello.jello_app.user.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String bio;
    private String password;
}
