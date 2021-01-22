package com.clj.demo.web;

import com.clj.demo.common.OutCome;
import com.clj.demo.entity.User;
import com.clj.demo.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * @Author lujia chen
 * @Created 2021/1/22
 * @Description powermock测试应用
 * @date 2021/1/22
 * @Version 1.0.version
 **/
@RunWith(PowerMockRunner.class)
@PrepareForTest({UserController.class, User.class})
//@PowerMockIgnore
public class UserControllerTest4 {
    //@Autowired属性的注入方式：联合使用@Mock和@InJeckMocks;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private UserController userController;

    /**
     * mock普通方法
     */
    @Test
    public void testAddUser() {
        User user = new User();
        PowerMockito.when(userService.createUser(user)).thenReturn(user);
        OutCome result = userController.createUser(user);
        Assert.assertEquals(result.getData(), true);
    }

    /**
     * mock抛出异常
     */
    @Test
    public void testDeleteUser() {
        Long toDelete = 1L;
        // 如果 user service 中的 delUser() 方法抛出的是 checked exception，那么，thenThrow() 里需要抛出 Exception()或者其子类；
        // 如果delUser() 方法抛出的是 unchecked exception，那么，thenThrow() 里需要抛出 RuntimeException()或其子类
        PowerMockito.when(userService.deleteUser(toDelete)).thenThrow(new Exception("mock exception"));
        OutCome outCome = userController.deleteUser(toDelete);
        Assert.assertEquals(outCome.getData(), true);
    }

    /**
     * mock静态方法
     */
    @Test
    public void mockUser() {
        PowerMockito.mockStatic(User.class);
        PowerMockito.when(User.getNickName("lucy")).thenReturn("lily");
        Assert.assertEquals(User.getNickName("lucy"), "lily");
    }

    /**
     * mock返回值为void的方法
     */
    @Test
    public void testSaveUser() throws Exception {
        User user = new User();
        //way one
        PowerMockito.doNothing().when(userService, "saveUser", user);
        //way two
        PowerMockito.doNothing().when(userService).saveUser(user);
        userController.saveUser(user);
    }

    /**
     * mock私有方法
     * ps:该方法中，还介绍了mock私有字段的值的方法。
     */
    @Test
    public void testModifyUser() throws Exception {
        User user = new User();
        Boolean result = true;
        PowerMockito.when(userService.modifyUser(user)).thenReturn(result);
        UserController userController2 = PowerMockito.mock(UserController.class);
        //给没有setter方法的私有字段赋值
        Whitebox.setInternalState(userController2, "userService", userService);
        //因为要测试的是modifyUser（）方法
        //所以，当调用这个方法时，应该让它调用真实的方法，而非被mock掉的方法
        PowerMockito.when(userController2.updateUser(user)).thenCallRealMethod();
        //在modifyUser方法中会调用verifyModifyUser()这个私有方法，所有，需要将mock掉
        PowerMockito.when(userController2, "updateUser", user).thenReturn(OutCome.success());
        OutCome<User> userOutCome = userController2.updateUser(user);
        Assert.assertEquals(userOutCome.getCode(), 200);
    }

    /**
     * mock私有方法
     * 方法二
     */
    @Test
    public void testModifyUser2() throws Exception {
        User user = new User();
        Boolean result = true;
        PowerMockito.when(userService.modifyUser(user)).thenReturn(result);
        //对controller进行监视
        userController = PowerMockito.spy(userController);
        //当controller的验证方法被执行是，将被mock掉
        PowerMockito.when(userController, "updateUser", user).thenReturn(user);
        OutCome<User> userOutCome = userController.updateUser(user);
        Assert.assertEquals(userOutCome.getCode(), 200);
    }

    /**
     * 参数的模糊匹配
     */
    @Test
    public void mockUser2() {
        PowerMockito.mockStatic(User.class);
        PowerMockito.when(User.getNickName(Matchers.anyString())).thenReturn("lily");
        Assert.assertEquals(User.getNickName("lucy"), "lily");
        Assert.assertEquals(User.getNickName("hanmeimei"), "lily");
    }
}
