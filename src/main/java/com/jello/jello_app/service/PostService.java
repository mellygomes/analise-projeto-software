package com.jello.jello_app.service;

import com.jello.jello_app.dto.CreatePostRequest;
import com.jello.jello_app.model.Post;

public interface PostService {
    Post createPost(CreatePostRequest request);
}
