package com.kasprzak.kamil.demoapp.post.mapper;

import com.kasprzak.kamil.demoapp.post.CommentDTO;
import com.kasprzak.kamil.demoapp.post.PostDTO;
import com.kasprzak.kamil.demoapp.post.GetPostsResponse;
import com.kasprzak.kamil.demoapp.post.query.PostsQueryResult;

public class PostsQueryResultToPostsDTOMapper{


    public GetPostsResponse map(PostsQueryResult result) {
        return GetPostsResponse
                .builder()
                .posts(result.getPostEntities()
                        .stream()
                        .map(post -> PostDTO
                                .builder()
                                .userId(post.getUserEntity().getId())
                                .content(post.getContent())
                                .comments(post.getCommentEntities()
                                        .stream()
                                        .map(comment -> CommentDTO
                                                .builder()
                                                .id(comment.getId())
                                                .userId(comment.getId())
                                                .content(comment.getContent())
                                                .build())
                                        .toList())
                                .build())
                        .toList())
                .build();
    }
}
