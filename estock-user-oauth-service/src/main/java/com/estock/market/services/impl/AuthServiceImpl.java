package com.estock.market.services.impl;

import com.estock.market.dto.GenericTokenResponse;
import com.estock.market.dto.requests.UserRequest;
import com.estock.market.services.AuthService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    public AuthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GenericTokenResponse getAuthToken(UserRequest userRequest){
        String encodedString = clientId+":"+clientSecret;
        String sceretKey= new String(Base64.encodeBase64(encodedString.getBytes()));
        String url = "http://localhost:8084/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", "Basic " + sceretKey);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("username", userRequest.getUsername());
        requestBody.add("password", userRequest.getPassword());
        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<GenericTokenResponse> response = restTemplate.exchange(url,
                HttpMethod.POST, formEntity, GenericTokenResponse.class);
        return  response.getBody();
    }
}
