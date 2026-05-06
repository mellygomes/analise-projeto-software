package com.jello.jello_app.utils;

import com.jello.jello_app.model.Comment;
import com.jello.jello_app.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("securityUtils")
@RequiredArgsConstructor
public class SecurityUtils {
    private final CommentRepository commentRepository;

    public Boolean canDeleteComment(Long commentId, Authentication authentication) {
        String username = authentication.getName();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        Boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Boolean isCommentOwner = comment.getUser().getUsername().equals(username);

        Boolean isPostOwner = comment.getPost().getUser().getUsername().equals(username);

        return isAdmin || isCommentOwner || isPostOwner;
    }
}
