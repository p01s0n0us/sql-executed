package com.xiaobai.sql.model.support;

import lombok.Data;

/**
 * @author XinHuiChen
 */
@Data
public class ValidateResponse extends BaseResponse<Boolean> {
    public Long time;
}
