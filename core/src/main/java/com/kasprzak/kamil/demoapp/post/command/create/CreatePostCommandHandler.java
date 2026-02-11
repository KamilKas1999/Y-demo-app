package com.kasprzak.kamil.demoapp.post.command.create;

import com.kasprzak.kamil.demoapp.common.command.CommandHandler;
import com.kasprzak.kamil.demoapp.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreatePostCommandHandler implements CommandHandler<CreatePostCommand> {

    private final PostService userService;

    @Override
    public void handle(CreatePostCommand command) {
        userService.createPost(command.getUserId(), command.getContent());
    }
}
