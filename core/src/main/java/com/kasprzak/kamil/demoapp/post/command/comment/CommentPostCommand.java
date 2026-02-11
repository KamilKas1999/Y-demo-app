package com.kasprzak.kamil.demoapp.post.command.comment;

import com.kasprzak.kamil.demoapp.common.command.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentPostCommand implements Command {

    private long userId;
    private long postId;
    private String text;

}
