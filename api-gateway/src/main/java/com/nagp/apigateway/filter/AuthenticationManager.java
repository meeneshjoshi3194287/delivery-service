package com.nagp.apigateway.filter;

import com.nagp.apigateway.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private WebClient.Builder loadBalancedWebClientBuilder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        return get(authToken)
                .switchIfEmpty(Mono.empty())
                .map(user -> {
                    this.userName = user.getUsername();
                    return new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            null,
                            user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    );
                });
    }

    public String userName;
    private Mono<User> get(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("token", jwt);
        return loadBalancedWebClientBuilder.build().post()
                .uri("http://user-service/user/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bodyMap))
                .retrieve().bodyToMono(User.class);
    }

}
