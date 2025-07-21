package com.damon.listener;



import com.damon.swagger.UserRegisterRequest;
import com.damon.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;



@Component
public class EventListenerList {

    // 创建 SLF4J Logger 实例，用于记录本身的日志
    private static final Logger logger = LoggerFactory.getLogger(EventListenerList.class);

    private MailUtil mailUtil;

    @Async("")
    @EventListener
    @Order(1)
    public void userRegisterListener(UserRegisterRequest request){
        logger.info("userRegisterListener");

//        mailUtil.sendSimpleMailToOne();

    }

}
