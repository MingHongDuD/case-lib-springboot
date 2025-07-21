package com.damon.controller;

import com.damon.log.Loggable;
import com.damon.swagger.UserRegisterRequest;
import com.damon.swagger.UserRegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.apache.catalina.User;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
public interface FullApi {


     ResponseEntity<UserRegisterResponse> userRegister(@Parameter(in = Parameter.D) @Valid @RequestBody UserRegisterRequest body, HttpServletRequest request);
}
