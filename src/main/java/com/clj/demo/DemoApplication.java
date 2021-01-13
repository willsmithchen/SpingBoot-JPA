package com.clj.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author lujia chen
 * @Created 2020/12/21
 * @Description demo启动类配置
 * @date 2020/12/21
 * @Version 1.0.version
 **/
//@EnableR2dbcRepositories
@SpringBootApplication
//@EnableWebFlux
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
