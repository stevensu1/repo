package com.mybatisflex.test;

import com.mybatisflex.core.MybatisFlexBootstrap;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;

public class Util {

    public static <T> T getMapper(Class<T> type) {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/sys_demo?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        MybatisFlexBootstrap bootstrap = MybatisFlexBootstrap.getInstance()
                .setDataSource(dataSource)
                .setLogImpl(StdOutImpl.class) //日志输出配置
                .addMapper(type)
                .start();
        return bootstrap.getMapper(type);

    }

}
