package com.jello.jello_app.service.impl;

import com.jello.jello_app.dto.CreatePostRequest;
import com.jello.jello_app.model.Post;
import com.jello.jello_app.service.ImageService;
import com.jello.jello_app.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final ImageService imageService;

    @Override
    @Transactional
    public Post createPost(CreatePostRequest request) {
        return null;
    }
}
