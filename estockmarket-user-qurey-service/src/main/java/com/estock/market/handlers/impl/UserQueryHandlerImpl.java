package com.estock.market.handlers.impl;

import com.estock.market.dto.UserLookupResponse;
import com.estock.market.handlers.UserQueryHandler;
import com.estock.market.models.User;
import com.estock.market.queries.FindAllUsersQuery;
import com.estock.market.queries.FindUserById;
import com.estock.market.queries.SearchUsersQuery;
import com.estock.market.repositories.UserRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserRepository userRepository;

    public UserQueryHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryHandler
    @Override
    public UserLookupResponse getUserById(FindUserById findUserById) {
        Optional<User> user = userRepository.findById(findUserById.getId());
        return new UserLookupResponse(user.get());
    }

    @QueryHandler
    @Override
    public UserLookupResponse searchUsers(SearchUsersQuery searchUsersQuery) {
        var users = new ArrayList<>(userRepository.findByFilterRegex(searchUsersQuery.getFilter()));
        return new UserLookupResponse(users);
    }

    @QueryHandler
    @Override
    public UserLookupResponse findAllUsers(FindAllUsersQuery findAllUsersQuery) {
        var users = new ArrayList<>(userRepository.findAll());
        return new UserLookupResponse(users);
    }
}
