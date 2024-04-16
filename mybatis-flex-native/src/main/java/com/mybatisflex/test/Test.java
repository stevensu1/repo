package com.mybatisflex.test;

import com.mybatisflex.core.MybatisFlexBootstrap;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;

import java.util.List;

public class Test {

    public static void main(String[] args) {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/sys_demo?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        MybatisFlexBootstrap bootstrap = MybatisFlexBootstrap.getInstance()
                .setDataSource(dataSource)
                .setLogImpl(StdOutImpl.class) //日志输出配置
                .addMapper(SysUserMapper.class)
                .start();

        SysUserMapper mapper = bootstrap.getMapper(SysUserMapper.class);
        List<SysUser> sysUserList = mapper.selectAll();
        System.out.println(sysUserList);

    }
}
