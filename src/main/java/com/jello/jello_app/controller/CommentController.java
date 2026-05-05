package com.jello.jello_app.controller;

import com.jello.jello_app.dto.ApiResponse;
import com.jello.jello_app.dto.CommentDTO;
import com.jello.jello_app.dto.AddCommentRequest;
import com.jello.jello_app.model.Comment;
import com.jello.jello_app.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}/add")
    public ResponseEntity<ApiResponse> addComment(@PathVariable Long postId, @RequestBody AddCommentRequest request) {
        try{
            Comment newComment = commentService.addComment(request.getComment(), postId);
            CommentDTO commentResponse = commentService.commentDTOBuilder(newComment);
            return  ResponseEntity.ok(new ApiResponse("Comment added!", commentResponse));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<ApiResponse> getAllCommentsFromPost(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getAllCommentsFromPost(postId);
        return ResponseEntity.ok(new ApiResponse("Comments listed!", comments));
    }
}
