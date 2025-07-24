package com.damon.config.security;

import com.damon.config.error.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.AsyncHandlerInterceptor;


/**
 * 权限校验拦截器，验证 HTTP 请求头中的认证信息，通过外部服务检查用户权限。
 * <p>
 * 该拦截器针对 /damon/** 路径，调用外部认证服务验证 Auth 或 Authorization 头。
 * 如果校验通过，将用户标识（GID）设置到请求属性中；否则抛出未授权异常。
 * 支持动态启用/禁用权限校验，跳过特定路径（如 /damon/web-api/session/**）。
 * </p>
 *
 * @author damon du/minghongdud
 */
public class PermissionCheckInterceptor implements AsyncHandlerInterceptor {

    /**
     * HTTP 请求头：Authorization
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";
    /**
     * HTTP 请求头：Auth
     */
    public static final String HEADER_AUTH = "Auth";
    /**
     * 查询参数：请求路径
     */
    public static final String HEADER_PATH = "path";
    /**
     * 请求属性：用户标识
     */
    public static final String GID = "gid";
    private static final Logger logger = LoggerFactory.getLogger(PermissionCheckInterceptor.class);
    /**
     * 默认用户（测试环境）
     */
    private static final String DEFAULT_USER = "DEFAULT_USER";

    /**
     * 是否启用权限校验
     */
    @Value("${common.svcs.auth.active:false}")
    private Boolean active;

    /**
     * 外部认证服务 URL
     */
    @Value("${common.svcs.auth.checking.path}")
    private String authServiceUrl;

    /**
     * 在请求处理前验证用户权限。
     * <p>
     * 如果权限校验未启用（active=false）或请求路径为 /damon/web-api/session/**，直接设置默认用户并通过。
     * 否则，通过外部认证服务验证 Auth 头，成功则设置 GID，失败则抛出异常。
     * </p>
     *
     * @param request  HTTP 请求，包含 Auth 或 Authorization 头
     * @param response HTTP 响应
     * @param handler  处理器对象
     * @return true 表示继续处理请求，false 表示拦截请求
     * @throws UnauthorizedException 如果认证失败或外部服务调用异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnauthorizedException {
        String requestUri = request.getRequestURI();
        logger.debug("PermissionCheckInterceptor preHandle: uri={}, active={}", requestUri, active);

        // 跳过权限校验的场景：校验未启用或特定路径
        if (!active || request.getRequestURI().startsWith("/damon/web-api/session/")) {
            // 使用请求参数 user 或默认用户
            String user = request.getParameter("user") != null ? request.getParameter("user") : DEFAULT_USER;
            request.setAttribute(GID, user);
            logger.info("Bypassed permission check, set GID: {}", user);
            return true;
        }

        // 获取请求头中的 Auth 或 Authorization
        String authHeader = request.getHeader(HEADER_AUTH);
        if (authHeader == null) {
            authHeader = request.getHeader(HEADER_AUTHORIZATION);
        }
        if (authHeader == null) {
            logger.warn("Missing Auth or Authorization header for URI: {}", requestUri);
            throw new UnauthorizedException("401", "Missing authentication header");
        }

        return true;
    }
}
