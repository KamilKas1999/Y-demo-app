package com.kasprzak.kamil.demoapp.post.query;

import com.kasprzak.kamil.demoapp.post.PostEntity;
import com.kasprzak.kamil.demoapp.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostsQueryHandlerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostsQueryHandler handler;

    @Test
    void shouldReturnAllPostsWhenUserIdIsEmpty() {
        // given
        List<PostEntity> posts = List.of(
                mock(PostEntity.class),
                mock(PostEntity.class)
        );

        when(postService.getPosts()).thenReturn(posts);

        PostsQuery query = PostsQuery.builder()
                .userId(Optional.empty())
                .build();

        // when
        PostsQueryResult result = handler.handle(query);

        // then
        assertNotNull(result);
        assertEquals(posts, result.getPostEntities());

        verify(postService).getPosts();
        verify(postService, never()).getUserPosts(any());
    }

    @Test
    void shouldReturnUserPostsWhenUserIdIsPresent() {
        // given
        Long userId = 42L;

        List<PostEntity> posts = List.of(
                mock(PostEntity.class)
        );

        when(postService.getUserPosts(userId)).thenReturn(posts);

        PostsQuery query = PostsQuery.builder()
                .userId(Optional.of(userId))
                .build();

        // when
        PostsQueryResult result = handler.handle(query);

        // then
        assertNotNull(result);
        assertEquals(posts, result.getPostEntities());

        verify(postService).getUserPosts(userId);
        verify(postService, never()).getPosts();
    }
}