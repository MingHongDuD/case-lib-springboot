package com.damon;

import com.damon.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author damon du/minghongdud
 */
@EnableAsync
@SpringBootApplication
public class CoreApplication {

    private final ApplicationArguments applicationArguments;

    private final UserService userService;

    public CoreApplication(ApplicationArguments applicationArguments, UserService userService) {
        this.applicationArguments = applicationArguments;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }


    public ApplicationRunner applicationRunner(){
        return applicationArguments->{
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+"开始调用一步业务");
            long endTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "：调用异步业务结束，耗时：" + (endTime - startTime));
        }
    }

}
