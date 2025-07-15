package com.damon.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;


/**
 * 日志切面
 *
 * @author damon du/minghongdud
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 切入点，匹配com.damon.controller包下面的所有方法
     */
    @Pointcut("execution(public * com.damon.controller.*(..))")
    public void webLog() {
    }

    /**
     * 环绕通知
     */
    @Around(value = "webLog()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            logger.info("1. Around: 方法环绕开始.....");
            Object result = pjp.proceed();
            logger.info("3. Around: 方法环绕结束，结果是：{}", result);
            return result;
        } catch (Throwable e) {
            logger.error("出现异常：", e);
            // TODO 这里返回reposet 的error
            return null;
        }
    }

    @Before(value = "webLog()")
    public void before(JoinPoint joinPoint) {
        logger.info("2. Before：方法开始执行");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        // 打印请求内容
        logger.info("URL:{}", request.getRequestURL());
        logger.info("HTTP_METHOD:{}", request.getMethod());
        logger.info("IP:{}", request.getRemoteAddr());
        logger.info("CLASS_METHOD:{}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.info("ARGS:{}", Arrays.toString(joinPoint.getArgs()));
    }

    @After(value = "webLog()")
    public void after(JoinPoint joinPoint) {
        logger.info("4. After: 方法最后执行.....");
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        logger.info("5. AfterReturning: 方法的返回值: {}", ret);
    }

    @AfterThrowing(value = "webLog()")
    public void afterThrows(JoinPoint joinPoint) {
        logger.error("AfterThrowing: 方法异常时执行...........");
    }

}
