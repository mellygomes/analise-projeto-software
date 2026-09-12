package com.jello.jello_app.post.dto;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String title;
    private String content;
}
