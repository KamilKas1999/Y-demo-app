package com.kasprzak.kamil.demoapp.post.service;

import com.kasprzak.kamil.demoapp.post.CommentEntity;
import com.kasprzak.kamil.demoapp.post.CommentRepository;
import com.kasprzak.kamil.demoapp.post.PostEntity;
import com.kasprzak.kamil.demoapp.post.PostRepository;
import com.kasprzak.kamil.demoapp.user.UserEntity;
import com.kasprzak.kamil.demoapp.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultPostServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private DefaultPostService service;

    @Test
    void shouldCreatePost() {
        // given
        long userId = 1L;
        String content = "test content";

        UserEntity user = mock(UserEntity.class);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        // when
        service.createPost(userId, content);

        // then
         verify(userRepository).findById(userId);
        verify(postRepository).save(any(PostEntity.class));
    }

    @Test
    void shouldReturnAllPosts() {
        // given
        List<PostEntity> posts = List.of(
                mock(PostEntity.class),
                mock(PostEntity.class)
        );

        when(postRepository.findAll()).thenReturn(posts);

        // when
        List<PostEntity> result = service.getPosts();

        // then
        assertEquals(posts, result);
        verify(postRepository).findAll();
    }

    @Test
    void shouldReturnUserPosts() {
        // given
        Long userId = 10L;
        List<PostEntity> posts = List.of(mock(PostEntity.class));

        when(postRepository.findByUserEntity_Id(userId)).thenReturn(posts);

        // when
        List<PostEntity> result = service.getUserPosts(userId);

        // then
        assertEquals(posts, result);
        verify(postRepository).findByUserEntity_Id(userId);
    }

    @Test
    void shouldDeletePost() {
        // given
        long postId = 5L;

        // when
        service.deletePost(postId);

        // then
        verify(postRepository).deleteById(postId);
    }

    @Test
    void shouldAddCommentToPost() {
        // given
        long postId = 1L;
        long userId = 2L;
        String content = "comment";

        PostEntity post = mock(PostEntity.class);
        List<CommentEntity> comments = new ArrayList<>();

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));
        when(post.getCommentEntities())
                .thenReturn(comments);

        // when
        service.addComment(postId, userId, content);

        // then
        verify(postRepository).findById(postId);
        verify(commentRepository).save(any(CommentEntity.class));
        verify(postRepository).save(post);
        assertEquals(1, comments.size());
    }

    @Test
    void shouldThrowExceptionWhenPostNotFoundWhileAddingComment() {
        // given
        long postId = 99L;

        when(postRepository.findById(postId))
                .thenReturn(Optional.empty());

        // when / then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.addComment(postId, 1L, "content")
        );

        assertEquals("Post not found", exception.getMessage());
    }
}