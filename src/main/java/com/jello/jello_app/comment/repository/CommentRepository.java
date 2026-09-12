package com.jello.jello_app.comment.repository;

import com.jello.jello_app.comment.model.Comment;
import com.jello.jello_app.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post postId);
}
