package com.estock.market.repositories;

import com.estock.market.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'$or': [{'firstName':{$regex: ?0, $options: '1i'}}, {'lastName':{$regex: ?0, $options: '1i'}}," +
            "{'emailAddress':{$regex: ?0, $options: '1'}}, {'account.username':{$regex: ?0, $options: '1i'}}]}")
    List<User> findByFilterRegex(String filter);
}
