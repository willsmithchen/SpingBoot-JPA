package com.clj.demo.entity;

import com.mysql.cj.util.LogUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

/**
 * @Author lujia chen
 * @Created 2021/1/21
 * @Description 测试mockito第二章
 * @date 2021/1/21
 * @Version 1.0.version
 **/
@RunWith(MockitoJUnitRunner.class)
public class TestDemo2 {
    /**
     * @Mock:标注的对象对生成一个假的对象
     * @InjectMocks:通常加在被测试对象上，该对象不会被mock，会创建真实的对象，把其他的Mock的对象注入被测试对象
     */
    @Mock
    private List<String> list;

    /**
     * 怎么运用subbing
     */
    @Test
    public void how_to_use_subbing() {
        //return时thenReturn和doReturn功能是一样的。
        Mockito.when(list.get(0)).thenReturn("first");
        Mockito.doReturn("second").when(list).get(1);
        Assert.assertEquals("first", list.get(0));
        Assert.assertEquals("second", list.get(1));

        //有返回值throw时用thenThrow
        Mockito.when(list.get(Mockito.anyInt())).thenThrow(new RuntimeException());
        try {
            list.get(0);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void how_to_subbing_return_void_method() {
        //对无返回值的方法mock
        Mockito.doNothing().when(list).clear();
        list.clear();
        Mockito.verify(list, Mockito.times(1)).clear();
        //无返回值throw时用dothrow
        Mockito.doThrow(RuntimeException.class).when(list).clear();
        list.clear();
        Assert.fail();

    }
}
