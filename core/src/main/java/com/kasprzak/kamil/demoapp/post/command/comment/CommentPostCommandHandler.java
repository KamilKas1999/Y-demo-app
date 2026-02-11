package com.kasprzak.kamil.demoapp.post.command.comment;

import com.kasprzak.kamil.demoapp.common.command.CommandHandler;
import com.kasprzak.kamil.demoapp.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentPostCommandHandler implements CommandHandler<CommentPostCommand> {

    private final PostService userService;

    @Override
    public void handle(CommentPostCommand command) {
        userService.addComment(command.getPostId(), command.getUserId(), command.getText());
    }
}
