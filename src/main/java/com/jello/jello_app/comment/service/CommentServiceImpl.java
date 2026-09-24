package com.jello.jello_app.comment.service;

import com.jello.jello_app.auth.service.AuthService;
import com.jello.jello_app.comment.dto.CommentDTO;
import com.jello.jello_app.comment.model.Comment;
import com.jello.jello_app.comment.repository.CommentRepository;
import com.jello.jello_app.post.model.Post;
import com.jello.jello_app.post.service.PostService;
import com.jello.jello_app.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final AuthService authService;

    @Override
    public Comment addComment(String content, Long postId) {
        User user = authService.getAuthenticatedUser();
        Post post = postService.getPostById(postId);

        Comment comment = new Comment();
        try {
            comment.setContent(content);
            comment.setPost(post);
            comment.setUser(user);

            commentRepository.save(comment);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return comment;
    }

    @Override
    public List<CommentDTO> getAllCommentsFromPost(Post postId) {
        List<Comment> comments = commentRepository.findByPost(postId);

        return comments.stream()
                .map(com -> CommentDTO.builder()
                        .id(com.getId())
                        .postId(com.getPost().getId())
                        .user(com.getUser().getUsername())
                        .content(com.getContent())
                        .build())
                .toList();
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.findById(commentId)
                .ifPresentOrElse(commentRepository::delete, () -> {
                    throw new RuntimeException("Comment not found!");
                });
    }
}

