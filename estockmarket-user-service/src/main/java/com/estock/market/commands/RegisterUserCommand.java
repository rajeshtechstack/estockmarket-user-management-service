package com.estock.market.commands;

import com.estock.market.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class RegisterUserCommand {
    @TargetAggregateIdentifier
    private String id;
    @NotNull(message = "user details are required")
    @Valid
    private User user;

}
