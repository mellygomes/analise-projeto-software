package com.jello.jello_app.service.impl;

import com.jello.jello_app.dto.CreatePostRequest;
import com.jello.jello_app.model.Post;
import com.jello.jello_app.model.User;
import com.jello.jello_app.repository.PostRepository;
import com.jello.jello_app.service.ImageService;
import com.jello.jello_app.service.PostService;
import com.jello.jello_app.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final ImageService imageService;
    private final UserService userService;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public Post createPost(CreatePostRequest request, List<MultipartFile> images) {
        User user = userService.getAuthenticatedUser();

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        if(images != null && !images.isEmpty()){
            imageService.saveImageForPost(images, savedPost);
        }

        return savedPost;
    }
}
