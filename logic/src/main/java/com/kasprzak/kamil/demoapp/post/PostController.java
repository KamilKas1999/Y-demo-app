package com.kasprzak.kamil.demoapp.post;

import com.kasprzak.kamil.demoapp.common.command.CommandExecutor;
import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import com.kasprzak.kamil.demoapp.common.query.QueryExecutor;
import com.kasprzak.kamil.demoapp.post.command.comment.CommentPostCommand;
import com.kasprzak.kamil.demoapp.post.command.create.CreatePostCommand;
import com.kasprzak.kamil.demoapp.post.mapper.PostsQueryResultToPostsDTOMapper;
import com.kasprzak.kamil.demoapp.post.query.PostsQuery;
import com.kasprzak.kamil.demoapp.post.query.PostsQueryResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private QueryExecutor queryExecutor;

    private CommandExecutor commandExecutor;

    private PostsQueryResultToPostsDTOMapper mapper;

    @PostMapping
    public void createPost(@RequestBody final CreatePostRequest createPostRequest) {
        final var command = new CreatePostCommand(createPostRequest.getUserId(), createPostRequest.getContent());
        commandExecutor.execute(command);
    }

    @GetMapping
    public GetPostsResponse getPosts() throws BusinesException {
        var query = new PostsQuery(Optional.empty());
        var result = queryExecutor.execute(query, PostsQueryResult.class);
        return mapper.map(result);
    }

    @GetMapping("/{userId}")
    public GetPostsResponse getPostsByUser(@PathVariable Long userId) throws BusinesException {
        var query = new PostsQuery(Optional.of(userId));
        var result = queryExecutor.execute(query, PostsQueryResult.class);
        return mapper.map(result);
    }

    @PostMapping("/comment")
    public void commentPost(@RequestBody final CommentPostRequest commentPostRequest) {
        var command = CommentPostCommand
                .builder()
                .postId(commentPostRequest.getPostId())
                .userId(commentPostRequest.getUserId())
                .text(commentPostRequest.getContent())
                .build();
        commandExecutor.execute(command);
    }
}
