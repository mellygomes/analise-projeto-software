package com.jello.jello_app.repository;

import com.jello.jello_app.model.Follow;
import com.jello.jello_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerAndFollowing(User follower, User following);

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    @Query("""
            SELECT f.following
            FROM Follow f
            WHERE f.follower.id = :userId
            """)
    List<User> findUsersFollowedBy(@Param("userId") Long userId);
}
