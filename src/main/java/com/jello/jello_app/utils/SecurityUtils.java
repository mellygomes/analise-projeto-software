package com.jello.jello_app.utils;

import com.jello.jello_app.model.Comment;
import com.jello.jello_app.model.Post;
import com.jello.jello_app.repository.CommentRepository;
import com.jello.jello_app.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("securityUtils")
@RequiredArgsConstructor
public class SecurityUtils {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

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

    public Boolean canModifyPost(Long postId, Authentication authentication) {
        String username = authentication.getName();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        Boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Boolean isPostOwner = post.getUser().getUsername().equals(username);

        return isAdmin || isPostOwner;
    }
}
