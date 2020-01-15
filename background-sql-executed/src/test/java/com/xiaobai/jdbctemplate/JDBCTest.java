package com.xiaobai.jdbctemplate;

import com.xiaobai.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JDBCTest {
    @Autowired
    JdbcTemplate selectUpdate;

    @Autowired
    JdbcTemplate deleteInsert;

    @Test
    public void jdbctest() {
    }

}
