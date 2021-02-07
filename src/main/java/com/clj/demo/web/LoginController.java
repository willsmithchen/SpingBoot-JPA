package com.clj.demo.web;

import com.clj.demo.common.OutCome;
import com.clj.demo.entity.User;
import com.clj.demo.service.UserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author lujia chen
 * @Created 2021/1/22
 * @Description 用户登录管理
 * @date 2021/1/22
 * @Version 1.0.version
 **/
@Api(tags = "登录管理")
@RestController
@RequestMapping(value = "/login-manager")
public class LoginController {
    @Resource
    private UserService userService;

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "用户密码", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public OutCome login(String username, String password) {
        User login = userService.login(username, password);
        if (Objects.isNull(login)) {
            return OutCome.failure().setMessage("此用户不存在");
        }
        return OutCome.success().setData(login);
    }

    @ApiOperation(value = "注册用户")
    @PostMapping(value = "/register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件流对象,接收数组格式", required = true,dataType = "MultipartFile")
    })
    public OutCome register(@RequestParam(value = "file") MultipartFile file, @RequestBody User user) {
        Boolean result = userService.register(user, file);
        if (result) {
            return OutCome.success().setMessage("注册成功");
        }
        return OutCome.failure().setMessage("注册失败");
    }
}
