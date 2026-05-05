package com.jello.jello_app.service.impl;

import com.jello.jello_app.dto.CommentDTO;
import com.jello.jello_app.model.Comment;
import com.jello.jello_app.model.Post;
import com.jello.jello_app.model.User;
import com.jello.jello_app.repository.CommentRepository;
import com.jello.jello_app.repository.UserRepository;
import com.jello.jello_app.service.CommentService;
import com.jello.jello_app.service.PostService;
import com.jello.jello_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Override
    public Comment addComment(String content, Long postId) {
        User user = userService.getAuthenticatedUser();
        Post post = postService.getPostById(postId);

        Comment comment = new Comment();
        try{
            comment.setContent(content);
            comment.setPost(post);
            comment.setUser(user);

            commentRepository.save(comment);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return comment;
    }

    @Override
    public CommentDTO commentDTOBuilder(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .user(comment.getUser().getUsername())
                .content(comment.getContent())
                .build();
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
}

