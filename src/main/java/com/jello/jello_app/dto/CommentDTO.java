package com.jello.jello_app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    private Long id;
    private Long postId;
    private String user;
    private String content;
}
