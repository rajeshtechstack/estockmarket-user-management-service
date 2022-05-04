package com.estock.market.handlers.impl;

import com.estock.market.events.UserDeletedEvent;
import com.estock.market.events.UserRegisteredEvent;
import com.estock.market.events.UserUpdatedEvent;
import com.estock.market.handlers.UserEventHandler;
import com.estock.market.repositories.UserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;

    public UserEventHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    @Override
    public void on(UserRegisteredEvent userRegisteredEvent) {
        userRepository.save(userRegisteredEvent.getUser());
    }

    @EventHandler
    @Override
    public void on(UserUpdatedEvent userUpdatedEvent) {
        userRepository.save(userUpdatedEvent.getUser());
    }

    @EventHandler
    @Override
    public void on(UserDeletedEvent userDeletedEvent) {
        userRepository.deleteById(userDeletedEvent.getId());
    }
}
