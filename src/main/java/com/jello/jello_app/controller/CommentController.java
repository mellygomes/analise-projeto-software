package com.jello.jello_app.controller;

import com.jello.jello_app.dto.ApiResponse;
import com.jello.jello_app.dto.CommentDTO;
import com.jello.jello_app.model.Comment;
import com.jello.jello_app.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}/add")
    public ResponseEntity<ApiResponse> addComment(@PathVariable Long postId, @RequestBody String comment) {
        try{
            Comment newComment = commentService.addComment(comment, postId);
            CommentDTO commentResponse = commentService.commentDTOBuilder(newComment);
            return  ResponseEntity.ok(new ApiResponse("Comment added!", commentResponse));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
