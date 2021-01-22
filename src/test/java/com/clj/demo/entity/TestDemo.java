package com.clj.demo.entity;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

/**
 * @Author lujia chen
 * @Created 2020/12/24
 * @Description 普通测试类
 * @date 2020/12/24
 * @Version 1.0.version
 **/
@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
public class TestDemo {
    /**
     * 判断是否增加提示语
     *
     * @param unsettledSize -未结清笔数
     * @param coopSize      -结清笔数
     * @return
     */
    public static Boolean getPromptStatement(Integer unsettledSize, Integer coopSize) {
        if ((unsettledSize + coopSize) >= 10) {
            return true;
        }
        return false;
    }


    @Test
    public void test() {
        List mock = Mockito.mock(List.class);
        mock.add("1");
        mock.clear();
        //验证add和clear行为是否发生
        Mockito.verify(mock).add("1");
        Mockito.verify(mock).clear();
    }

    @Test
    public void when_thenReturn() {
        //mock一个Iterator类
        Iterator iterator = Mockito.mock(Iterator.class);
        //预设当iterator调用next（）时第一次返回hello，第n次都返回world
        Mockito.when(iterator.next()).thenReturn("hello").thenReturn("world");
        //使用mock对象
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        Assert.assertEquals("hello world world", result);
    }

  /*  @Test
    public void when_thenThrow() throws IOException {
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        //预设当流关闭是抛出异常
        Mockito.doThrow(new IOException()).when(outputStream).close();
        outputStream.close();
    }*/

    /**
     * 重置mock
     */
    @Test
    public void reset_mock() {
        List list = Mockito.mock(List.class);
        Mockito.when(list.size()).thenReturn(10);
        list.add(1);
        Assert.assertEquals(10, list.size());
        //重置mock，清除所有的互动和预设
        Mockito.reset(list);
        Assert.assertEquals(0, list.size());
    }

    /**
     * 验证确切的调用次数
     */
    @Test
    public void verify_number_of_invocations() {
        List mock = Mockito.mock(List.class);
        mock.add(1);
        mock.add(2);
        mock.add(2);
        mock.add(3);
        mock.add(3);
        mock.add(3);
        //验证是否被调用一次，等效于下面的times（1）
        Mockito.verify(mock).add(1);
        Mockito.verify(mock, Mockito.times(1)).add(1);
        //验证是否被调用2次
        Mockito.verify(mock, Mockito.times(2)).add(2);
        //验证是否被调用3次
        Mockito.verify(mock, Mockito.times(3)).add(3);
        //验证是否被调用4次
        Mockito.verify(mock, Mockito.never()).add(4);
        //验证至少调用一次
        Mockito.verify(mock, Mockito.atLeastOnce()).add(1);
        //验证至少调用2次
        Mockito.verify(mock, Mockito.atLeast(2)).add(2);
        //验证至多调用3次
        Mockito.verify(mock, Mockito.atMost(3)).add(3);

    }
}
