package com.nagp.userservice.controller;

import com.nagp.userservice.entity.UserEntity;
import com.nagp.userservice.service.UserService;
import com.nagp.userservice.model.AuthRequest;
import com.nagp.userservice.model.AuthResponse;
import com.nagp.userservice.util.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private int port;

    @PostMapping(path = "/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody @Valid AuthRequest pBody) {
        logger.info("Working from port " + port + " of user service");
        return userService.getToken(pBody).filter(authResponse -> authResponse != null)
                .map(authResponse -> ResponseEntity.ok(authResponse))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping(path = "/signup", produces = "text/html")
    public String signup(@RequestBody @Valid AuthRequest pBody) {
        logger.info("Working from port " + port + " of user service");
        userService.save(pBody);
        return "Signed up successfully !!";
    }

    @PostMapping(path = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public User verify(@RequestBody @Valid AuthResponse pBody) throws AuthenticationException {
        logger.info("Working from port " + port + " of user service");
        UserEntity entity = userService.findByToken(pBody.getToken());
        return new User(entity);
    }

}
