package com.kasprzak.kamil.demoapp.post.service;

import com.kasprzak.kamil.demoapp.post.PostEntity;

import java.util.List;

public interface PostService {


    void createPost(final long userId, final String content);

    List<PostEntity> getPosts();

    public List<PostEntity> getUserPosts(Long userId);

    void deletePost(long id);

    void addComment(final long postId, final long userId, final String content);
}
