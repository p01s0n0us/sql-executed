package com.xiaobai.sql.validate;

import com.xiaobai.sql.enums.SQLType;

/**
 * @author XinHuiChen
 */
public interface Validatable {
    /**
     * compare the results of the sql written by the user between the sql of the specific id
     *
     * @param sql the sql write by the user
     * @param answerSql  the id of answer sql of SELECT
     * @return
     */
    boolean validate(String sql, String answerSql, String validateSql, SQLType type);
}
