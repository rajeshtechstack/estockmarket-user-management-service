package com.estock.market.handlers;

import com.estock.market.events.UserDeletedEvent;
import com.estock.market.events.UserRegisteredEvent;
import com.estock.market.events.UserUpdatedEvent;

public interface UserEventHandler {

   void on(UserRegisteredEvent userRegisteredEvent);
   void on(UserUpdatedEvent userUpdatedEvent);
   void on(UserDeletedEvent userDeletedEvent);
}
