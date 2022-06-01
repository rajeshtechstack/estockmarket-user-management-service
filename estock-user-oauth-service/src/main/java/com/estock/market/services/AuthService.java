package com.estock.market.services;

import com.estock.market.dto.GenericTokenResponse;
import com.estock.market.dto.requests.UserRequest;

public interface AuthService {
    GenericTokenResponse getAuthToken(UserRequest userRequest);
}
