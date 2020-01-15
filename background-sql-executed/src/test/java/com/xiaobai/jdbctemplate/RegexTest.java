package com.xiaobai.jdbctemplate;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void table() {
        String line = "update course set teacherid=0 where teacherid=1";
        String pattern = "";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile("update ([a-z]*) set");

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
        } else {
            System.out.println("NO MATCH");
        }
    }
}
