package com.xiaobai.sql.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @author XinHuiChen
 */
@Slf4j
@Getter
@Setter
public class ResultDefinition {
    private int columns;

    private int rows;

    private Map<Integer, List<Object>> content;

    private Class[] classes;

    private ResultDefinition(Builder builder) {
        this.columns = builder.columns;
        this.rows = builder.rows;
        this.classes = builder.classes;
        this.content = builder.content;
    }

    public static class Builder {
        private int columns;

        private int rows;

        private ResultSetMetaData metaData;

        private Class[] classes;

        private ResultSet resultSet;

        private Map<Integer, List<Object>> content;

        public Builder bind(ResultSet resultSet) {
            this.resultSet = resultSet;
            return this;
        }

        Builder metaData() {
            try {
                metaData = resultSet.getMetaData();
            } catch (SQLException e) {
                log.info("Fail to add metadata, [{}]", e.getMessage());
                metaData = null;
            }
            return this;
        }

        Builder scale() {
            try {
                columns = metaData.getColumnCount();
                resultSet.last();
                rows = resultSet.getRow();
            } catch (SQLException e) {
                log.info("Fail to add column, [{}]", e.getMessage());
            } finally {
                try {
                    resultSet.beforeFirst();
                } catch (SQLException e) {
                    log.info("Fail to return to first set, [{}]", e.getMessage());
                }
            }
            return this;
        }

        Builder classes() {
            try {
                assert columns != 0;
                classes = new Class[columns];
                for (int i = 1; i <= columns; i++) {
                    classes[i - 1] = Class.forName(metaData.getColumnClassName(i));
                }
            } catch (ClassNotFoundException e) {
                log.info("Fail to find class, [{}]", e.getMessage());
            } catch (SQLException e) {
                log.info("Fail to add classes, [{}]", e.getMessage());
            }
            return this;
        }

        Builder content() {
            content = new HashMap<>(rows);
            try {
                while (resultSet.next()) {
                    List<Object> list = new ArrayList<>(columns);
                    for (int i = 0; i < columns; i++) {
                        Object obj = resultSet.getObject(i + 1, classes[i]);
                        list.add(obj);
                    }
                    content.put(resultSet.getRow(), list);
                }
            } catch (SQLException e) {
                log.info("Fail to add content, [{}]", e.getMessage());
            }
            return this;
        }

        public ResultDefinition build() {
            return new ResultDefinition(this.metaData().scale().classes().content());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ResultDefinition)) {
            return false;
        }

        ResultDefinition resultDefinition = (ResultDefinition) obj;
        if (resultDefinition.rows != rows || resultDefinition.columns != columns) {
            return false;
        }

        if (!Arrays.equals(this.getClasses(), resultDefinition.getClasses())) {
            return false;
        }

        for (int i = 1; i <= rows; i++) {
            List<Object> objects1 = content.get(i);
            List<Object> objects2 = resultDefinition.content.get(i);
            if (!CollectionUtils.isEqualCollection(objects1, objects2)) {
                return false;
            }
        }

        return true;
    }
}
