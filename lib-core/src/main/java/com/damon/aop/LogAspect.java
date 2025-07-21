package com.damon.aop;

import com.damon.log.Loggable;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 日志切面
 *
 * @author damon du/minghongdud
 */
@Aspect
@Component
public class LogAspect {

    // 创建 SLF4J Logger 实例，用于记录本身的日志
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    // 定义相关ID，用于日志追踪
    private static final String CORRELATION_ID_KEY = "CorrelationId";
    // 定义日志前缀，用于格式化日志输出
    private static final String LOG_PREFIX = "#####";
    // 创建线程安全的Map，用来缓存反射获得的字段，提高性能
    private static final Map<Class<?>, Map<String, Field>> FIELD_CACHE = new ConcurrentHashMap<>();
    // 定义敏感字段列表，防止记录敏感信息
    private static final List<String> SENSITIVE_FIELDS = Arrays.asList("password", "creditCard");
    // 用于非 Web 场景，如批处理的 Correlation Id
    private static final ThreadLocal<String> BATCH_CORRELATION_ID = new ThreadLocal<>();

    /**
     * 根据指定的日志级别，将消息写入日志系统。
     */
    public static void log(LogLevel logLevel, String format, Object... args) {
        switch (logLevel) {
            // TRACE 级别，记录最详细的调试信息
            case TRACE:
                logger.trace(format, args);
                break;
            // DEBUG 级别，记录调试信息
            case DEBUG:
                logger.debug(format, args);
                break;
            // INFO 级别，记录常规信息
            case INFO:
                logger.info(format, args);
                break;
            // WARN 级别，记录警告信息
            case WARN:
                logger.warn(format, args);
                break;
            // ERROR 级别，记录错误信息
            case ERROR:
                logger.error(format, args);
                break;
            // 默认情况，未知日志级别
            default:
                logger.warn("No suitable log level found: {} {}", format, args);
        }
    }

    /**
     * 设置全局 toString 风格为 JSON
     */
    @PostConstruct
    private void setToStringStyle() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
    }

    /**
     * 切入点，匹配任何类级别标注了Loggable类的所有方法 || 匹配任何在方法级别上标注了Loggable的方法
     */
    @Pointcut("@within(com.damon.log.Loggable) || @annotation(com.damon.log.Loggable)")
    public void loggableMethods() {
    }

    /**
     * 定义环绕通知，拦截 loggableMethods 切点匹配的方法
     */
    @Around(value = "loggableMethods()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名，包含方法名、参数类型等信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取 @Loggable 注解，优先方法级别，其次是类级别
        Loggable loggable = getLoggableAnnotation(method, joinPoint.getTarget().getClass());
        LogLevel logLevel = loggable.value();

        // 生成或获取 Correlation ID，用于日志追踪
        String correlationId = setCorrelationId(signature, method);

        // 记录方法开始日志
        // 格式：##### CorrelationId=<id>, <类名> <方法名> START EXECUTION #####
        log(logLevel, "{} {} {} {} START EXECUTION {}", LOG_PREFIX, CORRELATION_ID_KEY, correlationId, signature.getDeclaringTypeName(), method.getName());

        // 记录参数
        if (loggable.params() && joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            String params = formatParameters(method, joinPoint.getArgs(), loggable.specifParams());
            log(logLevel, "{} {} {} {} ARGS={}", LOG_PREFIX, CORRELATION_ID_KEY, correlationId, method.getName(), params);
        }

        /*
         * 执行方法并计时
         */
        long startTime = System.currentTimeMillis();
        try {
            // 执行目标方法，获取返回值
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();

            // 记录返回值
            if (loggable.result() && result != null) {
                log(logLevel, "{} {} {} {} Result: {}", LOG_PREFIX, CORRELATION_ID_KEY, correlationId, method.getName(), result);
            }

            // 记录结束和耗时
            // 格式：##### CorrelationId=<id>, <方法名> finished execution in <时间>ms #####
            log(logLevel, "{} {} {} {} finished execution in {}ms", LOG_PREFIX, CORRELATION_ID_KEY, correlationId,
                    method.getName(), endTime - startTime);

            return result;
        } catch (Throwable t) {
            // 格式：##### CorrelationId=<id>, <方法名> failed: <异常信息> #####
            log(LogLevel.ERROR, "{} {} {} {} failed: {}", LOG_PREFIX, CORRELATION_ID_KEY,
                    correlationId, method.getName(), t.getMessage());
            throw t;
        } finally {
            // 清理 MDC 中的 Correlation ID，防止内存泄漏
            MDC.remove(CORRELATION_ID_KEY);
        }
    }

    /**
     * 获取 @Loggable 注解的方法
     */
    private Loggable getLoggableAnnotation(Method method, Class<?> targetClass) {
        // 检查方法上的 @Loggable 注解
        Loggable methodAnnotation = method.getAnnotation(Loggable.class);
        // 优先返回方法级别注解，否则返回类级别
        return methodAnnotation != null ? methodAnnotation : targetClass.getAnnotation(Loggable.class);
    }

    /**
     * 设置或获取 Correlation ID
     */
    private String setCorrelationId(MethodSignature signature, Method method) {
        String correlationId = "";
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        try {
            // 如果方法在 Controller 类中
            if (signature.getDeclaringTypeName().contains("Controller")) {
                // 生成新的 UUID 作为 Correlation ID
                correlationId = UUID.randomUUID().toString();
                // 存储到 Web 请求上下文中，作用范围为当前 HTTP 请求
                requestAttributes.setAttribute(CORRELATION_ID_KEY, correlationId,
                        RequestAttributes.SCOPE_REQUEST);
            }
            // 如果在 Web 上下文中
            else {
                RequestContextHolder.getRequestAttributes();// 获取已有的 Correlation ID
                correlationId = (String) requestAttributes.getAttribute(CORRELATION_ID_KEY,
                        RequestAttributes.SCOPE_REQUEST);
            }
            // 存储到 MDC，供日志框架使用
            MDC.put(CORRELATION_ID_KEY, correlationId);
            return correlationId;
        } finally {
            // 清理 ThreadLocal 和 MDC，仅在非 Web 上下文（批处理或事件）中执行
            BATCH_CORRELATION_ID.remove();
            MDC.remove(CORRELATION_ID_KEY);
        }
    }

    /**
     * 格式化方法参数为字符串
     */
    private String formatParameters(Method method, Object[] args, String[] specifParams) {
        StringBuilder sb = new StringBuilder();
        List<String> specificFields = Arrays.asList(specifParams);
        // 遍历方法参数
        for (int i = 0; i < args.length; i++) {
            // 添加参数类型（如 com.damon.primary.User:）
            sb.append(method.getParameterTypes()[i].getName()).append(":");
            // 如果指定了特定字段（如 name、age）
            if (!specificFields.isEmpty()) {
                sb.append("(");
                // 遍历指定字段
                for (int j = 0; j < specificFields.size(); j++) {
                    String fieldName = specificFields.get(j);
                    // 跳过敏感字段（如 password）
                    if (SENSITIVE_FIELDS.contains(fieldName)) {
                        continue;
                    }
                    try {
                        int finalI = i;
                        // 从缓存获取字段
                        Field field = FIELD_CACHE.computeIfAbsent(args[i].getClass(), cls -> new ConcurrentHashMap<>())
                                // 如果缓存中没有，创建新 Field
                                .computeIfAbsent(fieldName, name -> {
                                    try {
                                        // 通过反射获取字段
                                        Field f = args[finalI].getClass().getDeclaredField(name);
                                        // 设置字段可访问
                                        f.setAccessible(true);
                                        // 返回字段
                                        return f;
                                    } catch (NoSuchFieldException e) {
                                        // 捕获字段不存在异常，抛出运行时异常
                                        throw new RuntimeException("Field not found: " + name, e);
                                    }
                                });
                        // 添加字段名和值（如 name=John）
                        sb.append(fieldName).append("=").append(field.get(args[i]));
                        // 如果不是最后一个字段，添加逗号
                        if (j < specificFields.size() - 1) {
                            sb.append(", ");
                        }
                    } catch (IllegalAccessException e) {
                        logger.error("Failed to access field: {}", fieldName, e);
                    }
                }
                sb.append(")");
            }
            // 如果未指定特定字段，直接添加参数的 toString（如 User{name=John}）
            else {
                sb.append(args[i]);
            }
            // 如果不是最后一个参数，添加逗号
            if (i < args.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

}
