package com.jello.jello_app.post.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String content;
}
