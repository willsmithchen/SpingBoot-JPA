package com.clj.demo.actuator;

import com.clj.demo.entity.User;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.stereotype.Component;

/**
 * @Author lujia chen
 * @Created 2021/1/22
 * @Description 自定义健康断点检查
 * @date 2021/1/22
 * @Version 1.0.version
 **/
@Component
@WebEndpoint(id = "sessions")
public class MyHealthEndpoint {
    /**
     * path:默认的基础路径是/actuator,如果一个断点配置的路径是sessions,那么它的全路径是/actuator/seesions
     *
     * @param name
     * @return
     * @Selector 的含义是让这个路径变成/actuator/sessions/{name}我们能从路径上获取一个入参
     * @WebEndpoint 就相当于声明一个@RestController的控制类
     * @ReadOperation get请求
     * @WriteOperation post请求
     * @DeleteOperation delete请求
     * 自定义管理断点的路径
     */
    @ReadOperation
    public String get(@Selector String name) {
        return "my name is " + name;
    }
}
