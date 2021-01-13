package com.clj.demo.entity;

import com.clj.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author lujia chen
 * @Created 2020/12/23
 * @Description
 * @date 2020/12/23
 * @Version 1.0.version
 **/
@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void test1() {
//        List<User> users = userService.findUsers();
        /*List<User> collect = users.stream().sorted(Comparator.comparing(User::getCreateTime)).collect(Collectors.toList());
        List<User> collect1 = users.stream().sorted(Comparator.comparing(User::getCreateTime).reversed()).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(collect1);*/
       /* User user = User.builder()
                .userType(1)
                .createTime("2021-01-08")
                .name("魏晨")
                .password("weichen")
                .delFlag(1).build();
        System.out.println(user);*/
      /*  //获取所有id集合
        List<Long> ids = users.stream().map(User::getId).collect(Collectors.toList());
        System.out.println(ids);
        //根据id排序
        List<User> collect = users.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        System.out.println(collect);
        //分组
        Map<String, Long> map = users.stream().collect(Collectors.groupingBy(User::getName, Collectors.counting()));
        System.out.println(map);*/


    }
}