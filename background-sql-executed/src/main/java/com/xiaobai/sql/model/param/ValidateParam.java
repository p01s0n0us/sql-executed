package com.xiaobai.sql.model.param;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author XinHuiChen
 */
@Data
public class ValidateParam {
    private Integer id;

    private String type;

    private String sql;

    private String openid;
}
