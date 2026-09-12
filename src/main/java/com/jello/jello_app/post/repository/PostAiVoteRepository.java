package com.jello.jello_app.post.repository;

import com.jello.jello_app.post.model.PostAiVote;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostAiVoteRepository extends JpaRepository<PostAiVote, Long> {

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM PostAiVote pv
            WHERE pv.user.id = :userId AND pv.post.id = :postId
            """)
    int deleteByUserIdAndPostId(Long userId, Long postId);
}
