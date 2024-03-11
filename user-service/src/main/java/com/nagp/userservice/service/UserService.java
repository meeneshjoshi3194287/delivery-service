package com.nagp.userservice.service;

import com.nagp.userservice.entity.UserEntity;
import com.nagp.userservice.model.AuthRequest;
import com.nagp.userservice.model.AuthResponse;
import com.nagp.userservice.repo.UserRepository;
import com.nagp.userservice.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PBKDF2Encoder passwordEncoder;
    @Autowired
    private JWT jwt;

    Logger log = LoggerFactory.getLogger(UserService.class);

    public void save(AuthRequest pBody) {
        userRepository.save(new UserEntity(pBody.getUsername(), passwordEncoder.encode(pBody.getPassword())));
        log.info("----Congratulations , Your username " + pBody.getUsername() + " has been successfully created----");
    }

    public Mono<AuthResponse> getToken(AuthRequest pBody) {
        return Mono.justOrEmpty(userRepository.findByUsername(pBody.getUsername()).map(userEntity -> {
            boolean ans = passwordEncoder.encode(pBody.getPassword()).equals(userEntity.getPassword());
            return ans ? new AuthResponse(jwt.generateToken(userEntity)) : null;
        }).orElse(null));
    }

    public UserEntity findByToken(String token) throws AuthenticationException {
        String username = jwt.getUsernameFromToken(token);
        return userRepository.findByUsername(username).orElseThrow(() -> new AuthenticationException());
    }
}
