package com.jello.jello_app.repository;

import com.jello.jello_app.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    @Query("""
            SELECT p
            FROM Post p
            ORDER BY
                CASE
                    WHEN p.user.id IN :followingIds THEN 0
                    ELSE 1
                END,
                p.createdAt DESC
            """)
    List<Post> findFeedPosts(List<Long> followingIds);
}
