package com.estock.market.aggregates;

import com.estock.market.commands.DeleteUserCommand;
import com.estock.market.commands.RegisterUserCommand;
import com.estock.market.commands.UpdateUserCommand;
import com.estock.market.events.UserDeletedEvent;
import com.estock.market.events.UserRegisteredEvent;
import com.estock.market.events.UserUpdatedEvent;
import com.estock.market.models.User;
import com.estock.market.security.PasswordEncoder;
import com.estock.market.security.impl.PasswordEncoderImpl;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;
    private final PasswordEncoder passwordEncoder;

    public UserAggregate() {
        passwordEncoder = new PasswordEncoderImpl();
    }


    @CommandHandler
    public UserAggregate(RegisterUserCommand registerUserCommand) {
        var newUser = registerUserCommand.getUser();
        newUser.setId(registerUserCommand.getId());
        passwordEncoder = new PasswordEncoderImpl();
        var password = newUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        newUser.getAccount().setPassword(hashedPassword);

        var event = UserRegisteredEvent.builder()
                .id(registerUserCommand.getId())
                .user(newUser)
                .build();
        AggregateLifecycle.apply(event);

    }

    @CommandHandler
    public void handle(UpdateUserCommand updateUserCommand) {
        var updatedUser = updateUserCommand.getUser();
        updatedUser.setId(updateUserCommand.getId());
        var password = updatedUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        updatedUser.getAccount().setPassword(hashedPassword);

        var event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteUserCommand deleteUserCommand) {
        var event = new UserDeletedEvent();
        event.setId(deleteUserCommand.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent userRegisteredEvent){
        this.id = userRegisteredEvent.getId();
        this.user = userRegisteredEvent.getUser();

    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent userUpdatedEvent){

        this.user = userUpdatedEvent.getUser();
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent userDeletedEvent){

        AggregateLifecycle.markDeleted();
    }

}
