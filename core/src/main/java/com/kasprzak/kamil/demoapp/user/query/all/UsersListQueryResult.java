package com.kasprzak.kamil.demoapp.user.query.all;

import com.kasprzak.kamil.demoapp.common.query.QueryResult;
import com.kasprzak.kamil.demoapp.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class UsersListQueryResult implements QueryResult {

    List<UserEntity> userEntities;
}
