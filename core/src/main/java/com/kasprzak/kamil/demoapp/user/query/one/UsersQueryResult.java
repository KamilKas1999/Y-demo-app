package com.kasprzak.kamil.demoapp.user.query.one;

import com.kasprzak.kamil.demoapp.common.query.QueryResult;
import com.kasprzak.kamil.demoapp.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UsersQueryResult implements QueryResult {

    UserEntity userEntities;
}
