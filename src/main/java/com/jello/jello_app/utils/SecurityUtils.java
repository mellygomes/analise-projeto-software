package com.jello.jello_app.utils;

import com.jello.jello_app.model.Comment;
import com.jello.jello_app.model.Post;
import com.jello.jello_app.model.User;
import com.jello.jello_app.repository.CommentRepository;
import com.jello.jello_app.repository.PostRepository;
import com.jello.jello_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("securityUtils")
@RequiredArgsConstructor
public class SecurityUtils {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Boolean canDeleteComment(Long commentId, Authentication authentication) {
        String username = authentication.getName();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        User commentOwner = userRepository.findById(comment.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("Comment owner not found"));

        User postOwner = userRepository.findById(comment.getPost().getCreatedBy())
                .orElseThrow(() -> new RuntimeException("Post owner not found"));

        Boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Boolean isCommentOwner = commentOwner.getUsername().equals(username);

        Boolean isPostOwner = postOwner.getUsername().equals(username);

        return isAdmin || isCommentOwner || isPostOwner;
    }

    public Boolean canModifyPost(Long postId, Authentication authentication) {
        String username = authentication.getName();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        User postOwner = userRepository.findById(post.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("Post owner not found"));

        Boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Boolean isPostOwner = postOwner.getUsername().equals(username);

        return isAdmin || isPostOwner;
    }
}
