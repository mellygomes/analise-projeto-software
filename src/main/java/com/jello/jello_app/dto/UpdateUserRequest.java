package com.jello.jello_app.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String password;
}
