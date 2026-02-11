package com.kasprzak.kamil.demoapp.post.query;

import com.kasprzak.kamil.demoapp.common.query.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@AllArgsConstructor
@Getter
@Builder
public class PostsQuery implements Query<PostsQueryResult> {
    private Optional<Long> userId;
}
