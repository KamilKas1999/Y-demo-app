package com.kasprzak.kamil.demoapp.post.query;

import com.kasprzak.kamil.demoapp.common.query.QueryHandler;
import com.kasprzak.kamil.demoapp.post.PostEntity;
import com.kasprzak.kamil.demoapp.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostsQueryHandler implements QueryHandler<PostsQuery, PostsQueryResult> {

    private final PostService postService;

    public PostsQueryResult handle(PostsQuery query){
        List<PostEntity> posts = Collections.emptyList();
        if (query.getUserId().isEmpty()){
            posts =  postService.getPosts();
        }else{
            posts = postService.getUserPosts(query.getUserId().get());
        }
        return PostsQueryResult.builder()
                .postEntities(posts)
                .build();
    }

}
