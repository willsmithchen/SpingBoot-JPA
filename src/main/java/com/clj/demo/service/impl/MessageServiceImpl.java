package com.clj.demo.service.impl;

import com.clj.demo.common.OutCome;
import com.clj.demo.service.MessageService;
import com.clj.demo.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.testng.collections.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lujia chen
 * @Created 2021/2/24
 * @Description 消息实现类
 * @date 2021/2/24
 * @Version 1.0.version
 **/
@Slf4j
@Service("MessageService")
public class MessageServiceImpl implements MessageService {

    @Override
    public OutCome sendMessage(String phone, String text, String tplId) {
        String host = "http://yzx.market.alicloudapi.com";
        String path = "/yzx/sendSms";
        String method = "POST";
        String appcode = "137c5289c4fd49179067c3d1a26d5245";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", text);
        querys.put("tpl_id", tplId);
        Map<String, String> bodys = Maps.newHashMap();
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println("验证码发送成功：" + response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
            return OutCome.success().setMessage("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("验证码发送失败");
        }
        return null;
    }
}
