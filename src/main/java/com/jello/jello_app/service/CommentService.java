package com.jello.jello_app.service;

import com.jello.jello_app.dto.CommentDTO;
import com.jello.jello_app.model.Comment;

public interface CommentService {
    Comment addComment(String comment, Long postId);
    CommentDTO commentDTOBuilder(Comment comment);
}
