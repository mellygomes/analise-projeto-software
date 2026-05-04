package com.jello.jello_app.dto;

import lombok.Data;

@Data
public class CreatePostRequest {
    private Long id;
    private String title;
    private String content;

}
