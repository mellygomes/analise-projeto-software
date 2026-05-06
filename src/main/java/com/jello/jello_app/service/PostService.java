package com.jello.jello_app.service;

import com.jello.jello_app.dto.CreatePostRequest;
import com.jello.jello_app.dto.PostDTO;
import com.jello.jello_app.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    Post createPost(CreatePostRequest request, List<MultipartFile> images);
    PostDTO postDTOBuilder(Post post);
    Post getPostById(Long id);
    void deletePost(Long id);
    Post updatePost(CreatePostRequest request, Long postId);
}
