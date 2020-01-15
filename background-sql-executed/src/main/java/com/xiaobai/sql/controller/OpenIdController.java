package com.xiaobai.sql.controller;

import com.xiaobai.sql.config.SqlExecutedConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author XinHuiChen
 */
@RestController
@RequestMapping(value = "/user")
public class OpenIdController {

    private final RestTemplate restTemplate;

    public OpenIdController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    @ApiOperation(value = "获取openid")
    @GetMapping(value = "/login")
    public ResponseEntity<String> getOpenId(@RequestParam String code) {
        ResponseEntity<String> forEntity = restTemplate
                .getForEntity("https://api.weixin.qq.com/sns/jscode2session?appid={1}&secret={2}&js_code={3}&grant_type=authorization_code", String.class, SqlExecutedConfig.APP_ID, SqlExecutedConfig.App_Secret, code);
        return forEntity;
    }

}
