package com.jello.jello_app.comment.mapper;

import com.jello.jello_app.comment.dto.CommentDTO;
import com.jello.jello_app.comment.model.Comment;

public class CommentMapper {
    public static CommentDTO toDto(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .user(comment.getUser().getUsername())
                .content(comment.getContent())
                .build();
    }
}
