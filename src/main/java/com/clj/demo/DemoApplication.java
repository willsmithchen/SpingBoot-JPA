package com.clj.demo;

import com.clj.demo.spring.StartupHelper;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MapperConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author lujia chen
 * @Created 2020/12/21
 * @Description demo启动类配置
 * @date 2020/12/21
 * @Version 1.0.version
 **/
@Slf4j
@EnableAdminServer
@SpringBootApplication(scanBasePackages = "com.clj")
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
        StartupHelper.printStartInfo(log, ctx);
    }
}
