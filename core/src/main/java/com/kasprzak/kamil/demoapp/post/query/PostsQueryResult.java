package com.kasprzak.kamil.demoapp.post.query;

import com.kasprzak.kamil.demoapp.common.query.QueryResult;
import com.kasprzak.kamil.demoapp.post.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class PostsQueryResult implements QueryResult {

    List<PostEntity> postEntities;
}
