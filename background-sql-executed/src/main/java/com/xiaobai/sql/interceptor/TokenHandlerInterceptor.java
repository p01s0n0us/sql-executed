package com.xiaobai.sql.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xiaobai.sql.annotation.Token;
import com.xiaobai.sql.config.SqlExecutedConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author XinHui Chen
 * @date 2019/12/13 14:28
 */
public class TokenHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(Token.class)) {
            Token methodAnnotation = method.getAnnotation(Token.class);
            if (methodAnnotation.required()) {
                String token = getToken(request);
                if (token == null) {
                    response.sendError(404, "token is null");
                    return false;
                }
                try {
                    verify(token);
                } catch (JWTVerificationException e) {
                    response.sendError(404, e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token != null) {
            return token;
        }

        for (Cookie cookie : request.getCookies()) {
            if ("token"
                    .equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }

        return token;
    }

    private void verify(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SqlExecutedConfig.App_Secret))
                                     .build();
        jwtVerifier.verify(token);
    }
}
