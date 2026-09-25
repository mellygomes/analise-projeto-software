package com.jello.jello_app.post.service;

import com.jello.jello_app.auth.service.AuthService;
import com.jello.jello_app.follow.repository.FollowRepository;
import com.jello.jello_app.image.service.ImageService;
import com.jello.jello_app.post.dto.CreatePostRequest;
import com.jello.jello_app.post.model.Post;
import com.jello.jello_app.post.repository.PostAiVoteRepository;
import com.jello.jello_app.post.repository.PostRepository;
import com.jello.jello_app.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private ImageService imageService;

    @Mock
    private AuthService authService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private PostAiVoteRepository postAiVoteRepository;

    @InjectMocks
    private PostServiceImpl postService;

    // Teste para simular criação de post com fluxo normal
    @Test
    void shouldCreatePost() {
        CreatePostRequest request = new CreatePostRequest();
        request.setTitle("Post Mega Foda");
        request.setContent("Descrição do post insana");

        User user = new User();
        user.setId(1L);
        user.setUsername("nick");

        MultipartFile file1 = new MockMultipartFile(
                "file1",
                "foto-um.png",
                "image/png",
                "conteudo mesmo".getBytes()
        );

        MultipartFile file2 = new MockMultipartFile(
                "file2",
                "foto-dois.png",
                "image/png",
                "conteudo 2 mesmo".getBytes()
        );

        List<MultipartFile> files = List.of(file1, file2);

        when(authService.getAuthenticatedUser()).thenReturn(user);
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setId(1L);
            return post;
        });

        Post result = postService.createPost(request, files);

        assertEquals(request.getTitle(), result.getTitle());

        verify(authService, times(1)).getAuthenticatedUser();
        verify(imageService, times(1)).saveImageForPost(any(), any());
        verify(postRepository, times(1)).save(any());
    }

    // Teste para simular criação de post com dados invalidos
    @Test
    void shouldFailWhenCreatePostWithInvalidData() {
        CreatePostRequest request = new CreatePostRequest();
        request.setTitle("Post Mega Foda");
        request.setContent("Descrição do post insana");

        User user = new User();
        user.setId(1L);
        user.setUsername("nick");

        when(authService.getAuthenticatedUser()).thenReturn(user);
        when(postRepository.save(any(Post.class))).thenThrow(new RuntimeException("Erro ao criar post!"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> postService.createPost(request, null)
        );

        assertTrue(exception.getMessage().contains("Erro ao criar post!"));

        verify(authService, times(1)).getAuthenticatedUser();
        verify(postRepository, times(1)).save(any());
        verifyNoInteractions(imageService);
    }

    // Teste para recuperar post pelo ID
    @Test
    void shouldGetPostById() {
        Long postId = 1L;

        Post post = new Post();
        post.setId(2L);
        post.setTitle("Titulo post teste 2");
        post.setContent("Conteudo do post 2");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(postId);

        assertEquals(post.getTitle(), result.getTitle());

        verify(postRepository, times(1)).findById(anyLong());
    }

    // Teste para tentar recuperar post por ID quando ID é invalido
    @Test
    void shouldFailWhenGetPostWithInvalidId() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> postService.getPostById(postId));

        assertEquals("Post não encontrado!", exception.getMessage());
        verify(postRepository, times(1)).findById(anyLong());
    }

    // Teste para simular deleção de post por ID
    @Test
    void shouldDeletePostById() {
        Long postId = 1L;

        Post post = new Post();
        post.setId(2L);
        post.setTitle("Titulo post teste 2");
        post.setContent("Conteudo do post 2");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.deletePost(postId);

        verify(postRepository, times(1)).findById(anyLong());
        verify(postRepository, times(1)).delete(any(Post.class));
    }

    // Teste para simular deleção falha de post por ID invalido
    @Test
    void shouldFailWhenDeletePostById() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> postService.deletePost(postId));

        assertEquals("Falha ao deletar Post. Post não encontrado!", exception.getMessage());

        verify(postRepository, times(1)).findById(anyLong());
        verify(postRepository, times(0)).delete(any());
    }

    // Teste para simular atualizacao de post por ID
    @Test
    void shouldUpdatePostById() {
        CreatePostRequest request = new CreatePostRequest();
        request.setTitle("Post Mega Foda");
        request.setContent("Descrição do post insana");

        User user = new User();
        user.setId(1L);
        user.setUsername("nick");

        Long postId = 1L;

        Post post = new Post();
        post.setId(2L);
        post.setTitle("Titulo post teste 2");
        post.setContent("Conteudo do post 2");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(2L);
            return savedPost;
        });

        Post result = postService.updatePost(request, postId);

        assertEquals(post.getTitle(), result.getTitle());
        verify(postRepository, times(1)).findById(anyLong());
        verify(postRepository, times(1)).save(any());
    }

    // Teste para simular atualizacao de post por ID invalido
    @Test
    void shouldFailWhenUpdatePostWithInvalidId() {
        Long postId = 1L;

        CreatePostRequest request = new CreatePostRequest();
        request.setTitle("Post Mega Foda");
        request.setContent("Descrição do post insana");

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> postService.updatePost(request, postId));

        assertEquals("Falha ao atualizar o Post. Post não encontrado!", exception.getMessage());

        verify(postRepository, times(1)).findById(anyLong());
        verify(postRepository, never()).save(any());
    }

    // TODO: Criar teste para recuperar os posts do feed
    // Começei mas nao terminei
//    @Test
//    void shouldGetFeedPosts() {
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("fulano");
//
//        when(authService.getAuthenticatedUser()).thenReturn(user);
//    }

    // TODO: Criar teste para simular caminho falho ao tentar recuperar posts do feed
//    @Test
//    void shouldFailWhenGetFeedPosts() {}

    // TODO: Criar teste para incrementar o valor de contagem de feedback de IA
//    @Test
//    void shouldIncrement() {}

    // TODO: Criar teste que falha ao tentar incrementar o valor de contagem de feedback de IA
//    @Test
//    void shouldFailWhenIncrement() {}

    // TODO: Criar teste para decrementar o valor de contagem de feedback de IA
//    @Test
//    void shouldDecrement() {}

    // TODO: Criar teste que falha ao tentar decrementar o valor de contagem de feeback de IA
//    @Test
//    void shouldFailWhenDecrement() {}
}