package com.estock.market.controllers;

import com.estock.market.dto.UserLookupResponse;
import com.estock.market.queries.FindAllUsersQuery;
import com.estock.market.queries.FindUserById;
import com.estock.market.queries.SearchUsersQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class UserQueryController {

    private final QueryGateway queryGateway;

    public UserQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<UserLookupResponse> getAllUsers(){
        try{
            var query = new FindAllUsersQuery();
            var response = queryGateway
                    .query(query, ResponseTypes.instanceOf(UserLookupResponse.class))
                    .join();
            if(response == null || response.getUsers() == null){
                return new ResponseEntity<>(new UserLookupResponse("Data not found"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing get all users request ";
            return new ResponseEntity<>(new UserLookupResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable(value = "id") String id){
        try{
            var query = new FindUserById(id);
            var response = queryGateway
                    .query(query, ResponseTypes.instanceOf(UserLookupResponse.class))
                    .join();
            if(response == null || response.getUsers() == null){
                return new ResponseEntity<>(new UserLookupResponse("Data not found"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing get user by id request ";
            return new ResponseEntity<>(new UserLookupResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{filter}")
    public ResponseEntity<UserLookupResponse> searchUserByFilter(@PathVariable(value = "filter") String filter){
        try{
            var query = new SearchUsersQuery(filter);
            var response = queryGateway
                    .query(query, ResponseTypes.instanceOf(UserLookupResponse.class))
                    .join();
            if(response == null || response.getUsers() == null){
                return new ResponseEntity<>(new UserLookupResponse("Data not found"), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing user search by filter request ";
            return new ResponseEntity<>(new UserLookupResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
