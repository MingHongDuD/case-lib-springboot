package com.damon.controller;

import com.damon.swagger.UserRegisterRequest;
import com.damon.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制层
 *
 * @author damon du/minghongdud
 */
@RestController
@RequestMapping("lib/user")
public class UserController {

    private final UserService userService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public UserController(UserService userService,  ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 用户注册
     */
    @PostMapping(value = "/registerUser")
    public String registerUser(@RequestBody @Validated UserRegisterRequest userRegisterRequest) {
        applicationEventPublisher.publishEvent(userRegisterRequest);
        return userService.userRegister(userRegisterRequest);
    }

}
