package com.estock.market.dto;

import com.estock.market.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserLookupResponse extends BaseResponse{
    private List<User> users;

    public UserLookupResponse(String message) {
        super(message);

    }
    public UserLookupResponse(List<User> users) {
        super(null);
        this.users= users;

    }
    public UserLookupResponse(User user) {
        super(null);
        this.users = new ArrayList<>();
        this.users.add(user);
    }
    public UserLookupResponse(String message, User user) {
        super(message);
        this.users = new ArrayList<>();
        this.users.add(user);
    }
}
