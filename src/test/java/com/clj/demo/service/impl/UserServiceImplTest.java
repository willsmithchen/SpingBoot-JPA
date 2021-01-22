package com.clj.demo.service.impl;

import com.clj.demo.entity.User;
import com.clj.demo.repository.UserRepository;
import com.clj.demo.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @Author lujia chen
 * @Created 2021/1/21
 * @Description powermock学习
 * @date 2021/1/21
 * @Version 1.0.version
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({UserRepository.class, User.class})
public class UserServiceImplTest {

    @Test
    public void queryUserCount() {
        //powerMock的静态方法支持
        PowerMockito.mockStatic(UserRepository.class);
        //当调用repository的静态方法getCount时，返回10
        PowerMockito.when(UserRepository.getCount()).thenReturn(10L);
        //调用方法userservice.queryUserCount()的方法
        UserService userService = new UserServiceImpl();
        Long count = userService.getCount();
        //断言结果是否为预期值
        Assert.assertEquals(Long.valueOf(10), count);

    }

    @Test
    public void savaUser() {
        User user = new User();
        //powerMock的静态方法支持
        PowerMockito.mockStatic(UserRepository.class);
        //void返回类型doNothing
        PowerMockito.doNothing().when(UserRepository.class);
        //调用方法userservice.createUser(user)的方法
        UserService userService = new UserServiceImpl();
        userService.registUser(user);
        //验证是否执行成功
        PowerMockito.verifyStatic(UserService.class);
    }

    @Test
    public void mockUser() {
        PowerMockito.mockStatic(User.class);
        PowerMockito.when(User.getNickName("lucy")).thenReturn("lily");
        Assert.assertEquals(User.getNickName("lucy"), "lily");
    }
}