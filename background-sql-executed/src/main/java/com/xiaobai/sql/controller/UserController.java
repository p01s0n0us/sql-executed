package com.xiaobai.sql.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xiaobai.sql.annotation.Token;
import com.xiaobai.sql.config.SqlExecutedConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * @author XinHui Chen
 * @date 2019/12/13 14:43
 */
@RestController
@CrossOrigin(origins = "*")
public class UserController {


    @ApiOperation(value = "登陆", notes = "登陆")
    @PostMapping(value = "/login")
    public String login(String username, String password, HttpServletResponse httpServletResponse) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        String token = JWT.create()
                          .withAudience(username)
                          .withIssuedAt(new Date())
                          .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                          .sign(Algorithm.HMAC256(SqlExecutedConfig.App_Secret));
        jsonObject.put("token", token);
        Cookie cookie = new Cookie("token", token);
        httpServletResponse.addCookie(cookie);
        return jsonObject.toJSONString();
    }

    @ApiOperation(value = "登出", notes = "登出")
    @GetMapping(value = "/logout")
    @Token
    public void logout(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                cookie.setMaxAge(0);
            }
        }
    }

    @ApiOperation(value = "测试", notes = "测试")
    @GetMapping(value = "/mess")
    @Token
    public String getMessage() {
        return "message";
    }
}
