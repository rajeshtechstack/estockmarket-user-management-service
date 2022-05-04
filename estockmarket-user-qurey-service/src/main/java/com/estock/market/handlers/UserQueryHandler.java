package com.estock.market.handlers;

import com.estock.market.dto.UserLookupResponse;
import com.estock.market.queries.FindAllUsersQuery;
import com.estock.market.queries.FindUserById;
import com.estock.market.queries.SearchUsersQuery;

public interface UserQueryHandler {
    UserLookupResponse getUserById(FindUserById findUserById);
    UserLookupResponse searchUsers(SearchUsersQuery searchUsersQuery);
    UserLookupResponse findAllUsers(FindAllUsersQuery findAllUsersQuery);
}
