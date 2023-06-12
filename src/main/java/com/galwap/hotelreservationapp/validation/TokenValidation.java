package com.galwap.hotelreservationapp.validation;

import com.galwap.hotelreservationapp.exception.UnauthorisedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenValidation {

    public static final String TOKEN_KEY = "X-SECURITY-TOKEN";

    private final Map<String, UserDetails> userDetailsMap = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${authorization.url}")
    private String authUrl;

    public UserDetails validateToken(String token) throws UnauthorisedException {
        if (userDetailsMap.containsKey(token)) {
            return userDetailsMap.get(token);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(TOKEN_KEY, token);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<UserDetails> exchange = restTemplate.exchange(authUrl, HttpMethod.GET, httpEntity, UserDetails.class);

        if (exchange.getStatusCode() == HttpStatus.OK) {
            UserDetails userDetails = exchange.getBody();
            userDetailsMap.put(token, exchange.getBody());
            return userDetails;
        } else {
            throw new UnauthorisedException();
        }
    }
}
