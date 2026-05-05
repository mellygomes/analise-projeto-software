package com.jello.jello_app.controller;

import com.jello.jello_app.dto.ApiResponse;
import com.jello.jello_app.dto.CreatePostRequest;
import com.jello.jello_app.model.Post;
import com.jello.jello_app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPost(@RequestParam List<MultipartFile> images, @RequestBody CreatePostRequest request) {
        try {
            Post createdPost = postService.createPost(request, images);
            return ResponseEntity.ok(new ApiResponse("Post created successfully!", createdPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }
}
