package com.damon.service;

import com.damon.swagger.UserRegisterRequest;
import com.damon.util.MailUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * 用户逻辑处理层
 *
 * @author damon du/minghongdud
 */
@Service
public class UserService {

    private MailUtil mailUtil;

    /**
     * 用户注册
     */
    public String userRegister(UserRegisterRequest userRegisterRequest) {
        System.out.println("注册");
        return "43";
    }



    @Async
    public void asyncTask() {
        long startTime = System.currentTimeMillis();
        try {
            //模拟耗时
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "：void asyncTask()，耗时：" + (endTime - startTime));
    }

    @Async("asyncTaskExecutor")
    public Future<String> asyncTask(String s) {
        try {
            //模拟耗时
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "：Future<String> asyncTask(String s)，耗时：" + (endTime));
        return AsyncResult.forValue(s);
    }
}
