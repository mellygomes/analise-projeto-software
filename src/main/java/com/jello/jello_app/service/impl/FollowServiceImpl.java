package com.jello.jello_app.service.impl;

import com.jello.jello_app.model.Follow;
import com.jello.jello_app.model.User;
import com.jello.jello_app.repository.FollowRepository;
import com.jello.jello_app.service.FollowService;
import com.jello.jello_app.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final UserService userService;
    private final FollowRepository followRepository;

    @Override
    @Transactional
    public void followUser(Long userId) {
        User follower = userService.getAuthenticatedUser();
        User following = userService.getUserById(userId);

        if (follower.getId().equals(userId)) {
            throw new RuntimeException("You cannot follow yourself");
        }

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new RuntimeException("You already follow this user");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);
    }

    @Override
    @Transactional
    public void unfollowUser(Long userId) {
        User follower = userService.getAuthenticatedUser();
        User following = userService.getUserById(userId);

        if (follower.getId().equals(userId)) {
            throw new RuntimeException("You cannot unfollow yourself");
        }

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new RuntimeException("You do not follow this user"));

        followRepository.delete(follow);
    }
}
