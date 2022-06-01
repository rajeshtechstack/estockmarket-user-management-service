package com.estock.market.controllers;

import com.estock.market.commands.DeleteUserCommand;
import com.estock.market.commands.RegisterUserCommand;
import com.estock.market.commands.UpdateUserCommand;
import com.estock.market.dto.BaseResponse;
import com.estock.market.dto.RegisterUserResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1.0/user")
public class UserController {

    private final CommandGateway commandGateway;

    public UserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/registerUser")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<RegisterUserResponse> createUser(@Valid @RequestBody RegisterUserCommand registerUserCommand){
        registerUserCommand.setId(UUID.randomUUID().toString());
        try{
            commandGateway.send(registerUserCommand);
            return new ResponseEntity<>(new RegisterUserResponse(registerUserCommand.getId(), "User successfully registered"), HttpStatus.CREATED);
        }catch (Exception e){
            var errorMessage = "Error while executing register user request for id - "+registerUserCommand.getId();
            return new ResponseEntity<>(new RegisterUserResponse(registerUserCommand.getId(),errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable(value = "id") String id,
                                                   @Valid @RequestBody UpdateUserCommand updateUserCommand){
        try{
            updateUserCommand.setId(id);
            commandGateway.send(updateUserCommand);
            return new ResponseEntity<>(new BaseResponse("User successfully updated!!"), HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing update user request for id - "+id;
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> removeUser(@PathVariable(value = "id") String id){
        try{
            commandGateway.send(new DeleteUserCommand(id));
            return new ResponseEntity<>(new BaseResponse("User successfully removed!!"), HttpStatus.OK);
        }catch (Exception e){
            var errorMessage = "Error while executing remove user request for id - "+id;
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
