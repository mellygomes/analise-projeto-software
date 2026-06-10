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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    private final UserRepository userRepository;

    @Override
    public Comment addComment(String content, Long postId) {
        User user = userService.getAuthenticatedUser();
        Post post = postService.getPostById(postId);

        Comment comment = new Comment();
        try{
            comment.setContent(content);
            comment.setPost(post);
            comment.setCreatedBy(user.getId());

            commentRepository.save(comment);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return comment;
    }

    @Override
    public CommentDTO commentDTOBuilder(Comment comment) {
        Optional<User> user = userRepository.findById(comment.getCreatedBy());
        return CommentDTO.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .user(user.get().getUsername())
                .content(comment.getContent())
                .build();
    }

    @Override
    public List<CommentDTO> getAllCommentsFromPost(Post postId) {
        List<Comment> comments = commentRepository.findByPost(postId);
        List<CommentDTO> listComment = new ArrayList<>();

        for(Comment com : comments) {
            Optional<User> user = userRepository.findById(com.getCreatedBy());
            listComment.add(CommentDTO.builder()
                .id(com.getId())
                .postId(com.getPost().getId())
                .user(user.get().getUsername())
                .content(com.getContent())
                .build()
            );
        }

        return listComment;
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.findById(commentId)
                .ifPresentOrElse(commentRepository::delete, () -> {
                    throw new RuntimeException("Comment not found!");
                });
    }
}

