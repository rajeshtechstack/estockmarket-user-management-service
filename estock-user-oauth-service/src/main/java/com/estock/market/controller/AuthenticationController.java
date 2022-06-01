package com.estock.market.controller;

import com.estock.market.dto.GenericTokenResponse;
import com.estock.market.dto.requests.UserRequest;
import com.estock.market.services.AuthService;
import com.estock.market.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<GenericTokenResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        try{
            GenericTokenResponse genericTokenResponse = authService.getAuthToken(userRequest);
            return new ResponseEntity<>(genericTokenResponse, HttpStatus.OK);
        }catch (Exception e){
            //var errorMessage = "Error while executing register user request for id - "+userRequest.getId();
            return new ResponseEntity<>(new GenericTokenResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
