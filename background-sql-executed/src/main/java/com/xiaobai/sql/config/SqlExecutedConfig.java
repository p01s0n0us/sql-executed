package com.xiaobai.sql.config;

import com.xiaobai.sql.service.QuestionService;
import com.xiaobai.sql.validate.QueryValidator;
import com.xiaobai.sql.validate.UpdateValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

/**
 * @author XinHuiChen
 */
@Configuration
public class SqlExecutedConfig {

    public static final String APP_ID = "wx7f1517939019e36b";

    public static final String App_Secret = "8e77f9cdb94345a4482a32e7f759920c";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Scope("singleton")
    public QueryValidator queryValidator(@Qualifier("selectUpdateDataSource") DataSource dataSource,
                                         QuestionService questionService) {
        return new QueryValidator(dataSource, questionService);
    }

    @Bean
    @Scope("singleton")
    public UpdateValidator updateValidator(@Qualifier("deleteInsertDataSource") DataSource dataSource,
                                           QuestionService questionService,
                                           @Qualifier("selectUpdateDataSource") DataSource updateDataSource) {
        return new UpdateValidator(dataSource, questionService, updateDataSource);
    }
}
