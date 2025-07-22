package com.damon.service;

import com.damon.entity.primary.UserEntity;
import com.damon.repository.primary.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

public class RedisCacheInitializer implements ApplicationRunner {

    private final UserRepository userRepository;


    public RedisCacheInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<UserEntity> entityList = userRepository.findAll();
        String cacheKey = "CACHE_PREFIX:" + e;
    }
}
