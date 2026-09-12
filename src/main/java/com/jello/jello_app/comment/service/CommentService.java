package com.jello.jello_app.comment.service;

import com.jello.jello_app.comment.dto.CommentDTO;
import com.jello.jello_app.comment.model.Comment;
import com.jello.jello_app.post.model.Post;

import java.util.List;

public interface CommentService {
    Comment addComment(String comment, Long postId);
    CommentDTO commentDTOBuilder(Comment comment);
    List<CommentDTO> getAllCommentsFromPost(Post postId);
    void deleteComment(Long commentId);
}
