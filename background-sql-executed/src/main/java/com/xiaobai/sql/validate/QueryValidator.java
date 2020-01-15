package com.xiaobai.sql.validate;

import com.xiaobai.sql.enums.SQLType;
import com.xiaobai.sql.model.entity.ResultDefinition;
import com.xiaobai.sql.service.QuestionService;
import com.xiaobai.sql.utils.ConnectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author XinHuiChen
 */
@Slf4j
public class QueryValidator extends AbstractValidator {

    public QueryValidator(@Qualifier("selectUpdateDataSource") DataSource dataSource, QuestionService questionService) {
        super(dataSource, questionService);
    }

    @Override
    public ResultDefinition getResultDefinition(String sql, String validateSql, int level, SQLType type) {
        Connection connection = getConnection(level);
        if (connection == null) {
            return null;
        }
        Statement statement = null;
        ResultSet resultSet = null;
        ResultDefinition resultDefinition;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            resultDefinition = new ResultDefinition.Builder().bind(resultSet)
                                                             .build();
        } catch (SQLException e) {
            log.info("[{}] - Fail to get ResultDefinition, [{}]", type, e.getMessage());
            resultDefinition = null;
        } finally {
            ConnectionUtils.closeAll(resultSet, statement, connection);
        }
        return resultDefinition;
    }
}
