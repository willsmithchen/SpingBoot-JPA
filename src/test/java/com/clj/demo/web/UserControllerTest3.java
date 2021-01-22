package com.clj.demo.web;

import com.clj.demo.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @Author lujia chen
 * @Created 2021/1/21
 * @Description mockito测试controller层
 * @date 2021/1/21
 * @Version 1.0.version
 **/
@ContextConfiguration(classes = MockServletContext.class)
public class UserControllerTest3 {
    /**
     * 测试时URL参数应当与mock时的参数保持一致，否则mock不成功。
     */
    private MockMvc mockMvc;
    @Mock
    UserServiceImpl userService;
    @InjectMocks
    UserController userController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void mockitoTest() throws Exception {
        //mock void方法
        Mockito.doNothing().when(userService).deleteUser(Mockito.anyLong());
        //构建请求，与接口路劲一致
        RequestBuilder request = MockMvcRequestBuilders.get("/user-manager/1");
        //断言请求是否成功
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

    }

}
