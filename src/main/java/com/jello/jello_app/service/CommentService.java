package com.jello.jello_app.service;

import com.jello.jello_app.dto.CommentDTO;
import com.jello.jello_app.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(String comment, Long postId);
    CommentDTO commentDTOBuilder(Comment comment);
    List<CommentDTO> getAllCommentsFromPost(Long postId);
}
