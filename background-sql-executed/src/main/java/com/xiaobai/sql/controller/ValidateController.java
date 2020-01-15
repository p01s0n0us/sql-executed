package com.xiaobai.sql.controller;

import com.xiaobai.sql.enums.SQLType;
import com.xiaobai.sql.model.param.ValidateParam;
import com.xiaobai.sql.model.support.BaseResponse;
import com.xiaobai.sql.service.RecordService;
import com.xiaobai.sql.validate.QueryValidator;
import com.xiaobai.sql.validate.UpdateValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XinHuiChen
 */
@RestController
@Slf4j
public class ValidateController {
    private final QueryValidator queryValidator;

    private final UpdateValidator updateValidator;

    private final RecordService recordService;

    public ValidateController(QueryValidator queryValidator,
                              UpdateValidator updateValidator, RecordService recordService) {
        this.queryValidator = queryValidator;
        this.updateValidator = updateValidator;
        this.recordService = recordService;
    }

    @ApiOperation(value = "验证SQL语句是否正确")
    @PostMapping(value = "/validate")
    public BaseResponse<Object> validate(@RequestBody ValidateParam validateParam) {
        log.info("Request param: [{}]", validateParam);
        String sql = validateParam.getSql();
        Integer id = validateParam.getId();
        String openid = validateParam.getOpenid();
        String type = validateParam.getType();
        boolean correct;

        long startTime = System.currentTimeMillis();
        if (type.equalsIgnoreCase(SQLType.SELECT.name())) {
            correct = queryValidator.validate(sql, id, SQLType.SELECT);
        } else {
            correct = updateValidator.validate(sql, id, SQLType.valueOf(type));
        }
        long duration = System.currentTimeMillis() - startTime;

        recordService.addRecord(openid, correct, id, sql, correct ? duration : 0);
        return BaseResponse.ok(HttpStatus.OK.getReasonPhrase(), correct, correct ? duration : 0);
    }
}
