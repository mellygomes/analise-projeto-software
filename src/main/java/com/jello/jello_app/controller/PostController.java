package com.jello.jello_app.controller;

import com.jello.jello_app.dto.ApiResponse;
import com.jello.jello_app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
public class PostController {
    private final PostService postService;

    public ResponseEntity<ApiResponse> createPost(@RequestParam List<MultipartFile> images, Post)
}
