package com.clj.demo.configuration;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2021/6/7
 * @description 自定义数据源初始化
 * @date 2021/6/7
 **/
@Slf4j
//@Configuration
@Component
public class DbScriptInit {

    //    @Value(value = "classpath*:create.sql")
//    private String sqlScript;
//
//    @Bean
//    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
//        final DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        initializer.setDatabasePopulator(this.databasePopulator());
//        return initializer;
//    }
//
//    /**
//     * 初始化数据策略
//     *
//     * @return
//     */
//    private DatabasePopulator databasePopulator() {
//        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScripts(this.getResources());
//        return populator;
//    }
//
//    @SneakyThrows
//    private Resource[] getResources() {
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources(sqlScript);
//        log.info("加载初始化SQL脚本文件----Start");
//        for (Resource resource : resources) {
//            log.info(resource.getFilename());
//        }
//        log.info("加载初始化SQL脚本文件----End");
//        return resources;
//    }
    private static final String SAMPLE_DATA_SQL = "classpath:create.sql";
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void execute() throws SQLException, IOException {
        log.info("正在初始化模板sql数据......");
        ClassPathResource cr = new ClassPathResource("create.sql");
        EncodedResource er = new EncodedResource(cr, "utf-8");
        Connection connection = dataSource.getConnection();
        ScriptUtils.executeSqlScript(connection, er);
    }
}
