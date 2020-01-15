package com.xiaobai.sql.validate;

import com.xiaobai.sql.enums.SQLType;
import com.xiaobai.sql.model.entity.ResultDefinition;
import com.xiaobai.sql.service.QuestionService;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author XinHuiChen
 */
@Slf4j
public abstract class AbstractValidator implements Validatable {

    private final Pattern updateRegex = compile("update ([a-z]*) set");
    private final Pattern deleteRegex = compile("delete from ([a-z]*) where");
    private final Pattern insertRegex = compile("insert into ([a-z]*) values");

    private final DataSource dataSource;

    private final QuestionService questionService;

    AbstractValidator(DataSource dataSource, QuestionService questionService) {
        this.dataSource = dataSource;
        this.questionService = questionService;
    }


    public boolean validate(String sql, Integer id, SQLType type) {
        String answerSql = questionService.answerSql(id);
        if (type == (SQLType.SELECT)) {
            return validate(sql, answerSql, null, type);
        } else {
            String s = sql.toLowerCase();
            if (type == (SQLType.UPDATE)) {
                Matcher matcher = updateRegex.matcher(s);
                if (matcher.find()) {
                    String validateSql = "select * from " + matcher.group(1);
                    log.info("Update - Validate SQL: [{}]", validateSql);
                    return validate(sql, answerSql, validateSql, type);
                } else {
                    return false;
                }
            } else if (type == (SQLType.DELETE)) {
                Matcher matcher = deleteRegex.matcher(s);
                if (matcher.find()) {
                    String validateSql = "select * from " + matcher.group(1);
                    log.info("Delete - Validate SQL: [{}]", validateSql);
                    return validate(sql, answerSql, validateSql, type);
                } else {
                    return false;
                }
            } else if (type == (SQLType.INSERT)) {
                Matcher matcher = insertRegex.matcher(s);
                if (matcher.find()) {
                    String validateSql = "select * from " + matcher.group(1);
                    log.info("INSERT - Validate SQL: [{}]", validateSql);
                    return validate(sql, answerSql, validateSql, type);
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean validate(String sql, String answerSql, String validateSql, SQLType type) {
//        log.info("sql type: [{}], sql: [{}], answer sql: [{}], validate sql: [{}]", type, sql, answerSql, validateSql);
        ResultDefinition resultDefinition1 = getResultDefinition(sql, validateSql,
                                                                 Connection.TRANSACTION_READ_COMMITTED, type);
        if (resultDefinition1 == null) {
            return false;
        }

        ResultDefinition resultDefinition2 = getResultDefinition(answerSql, validateSql,
                                                                 Connection.TRANSACTION_READ_COMMITTED, type);
        if (resultDefinition2 == null) {
            return false;
        }
        return resultDefinition1.equals(resultDefinition2);
    }

    /**
     * @param sql
     * @param validateSql
     * @param level
     * @param type
     * @return
     */
    abstract ResultDefinition getResultDefinition(String sql, String validateSql, int level, SQLType type);

    Connection getConnection(int level) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(level);
            return connection;
        } catch (SQLException e) {
            log.info("Fail to get connection, {}", e.getMessage());
            return null;
        }
    }

}
