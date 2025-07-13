package com.damon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 *
 * @author damon du/minghongdud
 */
@RestController
@RequestMapping("lib/user")
public class UserController {

    @GetMapping(value = "")
    public String index() {
        return "this is index page, you can request /hello to get other fallback.";
    }

    @GetMapping(value = "hello")
    public String hello() {
        return "hello.";
    }
}
