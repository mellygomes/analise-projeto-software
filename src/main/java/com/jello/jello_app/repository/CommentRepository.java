package com.jello.jello_app.repository;

import com.jello.jello_app.model.Comment;
import com.jello.jello_app.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post postId);
}
