package com.jello.jello_app.repository;

import com.jello.jello_app.model.Follow;
import com.jello.jello_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("""
            SELECT f.following
            FROM Follow f
            WHERE f.follower.id = :userId
            """)
    List<User> findUsersFollowedBy(Long userId);
}
