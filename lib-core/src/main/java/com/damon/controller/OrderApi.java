package com.damon.controller;

import com.damon.entity.secondary.OrderEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface OrderApi {

    @PostMapping(value = "/ffi/web-api/flight/subscription")
    ResponseEntity<OrderEntity> get1231();
}
