package com.clj.demo.web;

import com.clj.demo.repository.UserRepository;
import com.clj.demo.service.UserService;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author lujia chen
 * @Created 2021/1/21
 * @Description
 * @date 2021/1/21
 * @Version 1.0.version
 **/
//使用这个跑单测，所有目标类调用的服务都需要mock，不会加载容器支持静态方法mock。
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PowerMockIgnore({"io.*", "org.*", "ch.*", "javax.validation.*"})
//如果有调用其他类中的静态方法，则需要指定被mock的静态资源类，没有的话则不需要。
//@PrepareForTest()
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    //    @InjectMocks
    @Autowired
    UserService userService;
    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        //这句话执行以后，dao层会自动注入到service层里面
        MockitoAnnotations.initMocks(this);
        //在这之后，可以放心大胆的使用when().then()、
        //doThrow(new RuntimeException()).when(object).someMethod(Mockito.any),

    }

    @Test
    public void testUserService() {
        List mock = Mockito.mock(List.class);
        mock.add("1");
        mock.clear();
        //验证add和clear行为是否发生
        Mockito.verify(mock).add("1");
        Mockito.verify(mock).clear();
    }
}