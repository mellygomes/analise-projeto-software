package com.jello.jello_app.service.impl;

import com.jello.jello_app.dto.CreatePostRequest;
import com.jello.jello_app.dto.PostDTO;
import com.jello.jello_app.model.Post;
import com.jello.jello_app.model.User;
import com.jello.jello_app.repository.FollowRepository;
import com.jello.jello_app.repository.PostRepository;
import com.jello.jello_app.service.ImageService;
import com.jello.jello_app.service.PostService;
import com.jello.jello_app.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final ImageService imageService;
    private final UserService userService;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    @Override
    @Transactional
    public Post createPost(CreatePostRequest request, List<MultipartFile> images) {

        Post savedPost = null;
        try {
            User user = userService.getAuthenticatedUser();

            Post post = new Post();
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setUser(user);

            savedPost = postRepository.save(post);

            if(images != null && !images.isEmpty()){
                imageService.saveImageForPost(images, savedPost);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return savedPost;
    }

    @Override
    public PostDTO postDTOBuilder(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found!"));
    }

    @Override
    public List<PostDTO> getFeedPosts() {
        User user = userService.getAuthenticatedUser();
        // USUARIO AUTENTICADO NAO ESTA SENDO RECEBIDO AQUI E DA ERRO
        SecurityContextHolder.getContext().getAuthentication();
        List<Long> followingIds = followRepository.findUsersFollowedBy(user.getId())
                .stream()
                .map(User::getId)
                .toList();

        List<Post> posts;

        if (followingIds.isEmpty()) {
            posts = postRepository.findAllByOrderByCreatedAtDesc();
        } else {
            posts = postRepository.findFeedPosts(followingIds);
        }

        return posts.stream()
                .map(this::postDTOBuilder)
                .toList();
    }
}
