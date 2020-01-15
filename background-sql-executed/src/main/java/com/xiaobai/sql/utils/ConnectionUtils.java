package com.xiaobai.sql.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * @author XinHuiChen
 */
@Slf4j
public class ConnectionUtils {

    private static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private static String URL = "jdbc:mysql:///sql_executed?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";

    private static String USERNAME = "root";

    private static String PASSWORD = "";

    private ConnectionUtils() {}

    public static void releaseConnection(Connection connection) {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            log.info("Fail to release connection, {}", e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_CLASS);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            log.info("Failed to load {}, {}", DRIVER_CLASS, e.getMessage());
        } catch (SQLException e) {
            log.info("Fail to get connection, {}", e.getMessage());
        }
        return null;
    }

    public static void closeAll(ResultSet rs, Statement sm, Connection cn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (sm != null) {
                sm.close();
            }
            if (cn != null) {
                releaseConnection(cn);
            }
        } catch (SQLException e) {
            log.info("Failed to close, {}", e.getMessage());
        }
    }
}
