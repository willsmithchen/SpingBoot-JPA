package com.clj.demo.web;

import com.clj.demo.entity.User;
import com.clj.demo.repository.UserRepository;
import com.clj.demo.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @Author lujia chen
 * @Created 2021/1/21
 * @Description 学习测试框架mockito
 * @date 2021/1/21
 * @Version 1.0.version
 **/
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest2 {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    /**
     * 测试service层
     */
    @Test
    public void testController() {
        User user = new User();
        //当dao层的增加方法被service层的注册方法调用时返回1
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        //验证方法调用
        Assert.assertTrue(userService.registUser(user));

    }

    /**
     * 异常测试
     */
    @Test
    public void mockitoTest() {
        User user = new User();
        //调用userRepository的修改方法抛出runtimeException
        Mockito.doThrow(new RuntimeException("operation notimplemented")).when(userRepository).saveAndFlush(Mockito.any(User.class));
        //异常后断言
        Assert.assertTrue(userService.modifyUser(user));
    }

}
