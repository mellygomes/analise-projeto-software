package com.jello.jello_app.post.service;

import com.jello.jello_app.auth.service.AuthService;
import com.jello.jello_app.follow.repository.FollowRepository;
import com.jello.jello_app.image.service.ImageService;
import com.jello.jello_app.post.dto.AiVoteResponseDTO;
import com.jello.jello_app.post.dto.CreatePostRequest;
import com.jello.jello_app.post.dto.PostDTO;
import com.jello.jello_app.post.mapper.PostMapper;
import com.jello.jello_app.post.model.Post;
import com.jello.jello_app.post.model.PostAiVote;
import com.jello.jello_app.post.repository.PostAiVoteRepository;
import com.jello.jello_app.post.repository.PostRepository;
import com.jello.jello_app.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ImageService imageService;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final PostAiVoteRepository postAiVoteRepository;

    @Override
    @Transactional
    public Post createPost(CreatePostRequest request, List<MultipartFile> images) {

        Post savedPost = null;
        try {
            User user = authService.getAuthenticatedUser();

            Post post = new Post();
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setUser(user);

            savedPost = postRepository.save(post);

            if (images != null && !images.isEmpty()) {
                imageService.saveImageForPost(images, savedPost);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return savedPost;
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found!"));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.findById(id)
                .ifPresentOrElse(postRepository::delete, () -> {
                    throw new RuntimeException("Post not found!");
                });
    }

    @Override
    public Post updatePost(CreatePostRequest request, Long postId) {
        return postRepository.findById(postId)
                .map(existingPost -> {
                    existingPost.setTitle(request.getTitle());
                    existingPost.setContent(request.getContent());
                    return postRepository.save(existingPost);
                })
                .orElseThrow(() -> new RuntimeException("Post not found!"));
    }

    @Override
    public Page<PostDTO> getFeedPosts(int page, int size) {
        User user = authService.getAuthenticatedUser();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        List<Long> followingIds = followRepository.findUsersFollowedBy(user.getId())
                .stream()
                .map(User::getId)
                .toList();

        Page<Post> posts;

        if (followingIds.isEmpty()) {
            posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            posts = postRepository.findFeedPosts(followingIds, pageable);
        }

        return posts.map(PostMapper::toDto);
    }

    @Override
    @Transactional
    public AiVoteResponseDTO incrementAiFeedback(Long postId) {

        User user = authService.getAuthenticatedUser();
        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);

        post.incrementAiCount();

        PostAiVote postAiVote = new PostAiVote();
        postAiVote.setUser(user);
        postAiVote.setPost(post);

        postAiVoteRepository.save(postAiVote);

        return new AiVoteResponseDTO(
                post.getAiCount(),
                true
        );
    }

    @Override
    @Transactional
    public AiVoteResponseDTO decrementAiFeedback(Long postId) {

        User user = authService.getAuthenticatedUser();

        int deletedRows = postAiVoteRepository.deleteByUserIdAndPostId(user.getId(), postId);
        if (deletedRows == 0) {
            throw new IllegalStateException("Usuário nao possui voto cadastrado nesse post.");
        }

        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        post.decrementAiCount();

        return new AiVoteResponseDTO(
                post.getAiCount(),
                false
        );
    }
}
