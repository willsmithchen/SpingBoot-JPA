package com.clj.demo.web;

import com.clj.demo.common.OutCome;
import com.clj.demo.entity.User;
import com.clj.demo.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author lujia chen
 * @Created 2020/12/21
 * @Description 用户控制层
 * @date 2020/12/21
 * @Version 1.0.version
 **/
@RestController
@Api(tags = "用户管理")
@RequestMapping(value = "/user-manager")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation(value = "查询用户")
    public OutCome<List<User>> findUser() {
        return OutCome.success(userService.findUsers());
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "单个查询")
    public OutCome<User> findUserById(@PathVariable Long id) {
        return OutCome.success(userService.findUserById(id));
    }

    @GetMapping(value = "/name/{userName}")
    @ApiOperation(value = "根据用户名查询")
    public OutCome<User> findUserById(@PathVariable @ApiParam String userName) {
        return OutCome.success(userService.findUserByUserName(userName));
    }

    @PostMapping
    @ApiOperation(value = "新增用户")
    public OutCome createUser(@RequestBody @ApiParam(value = "用户新增参数") User user) {
        userService.createUser(user);
        return OutCome.success().setMessage("新增成功");
    }

    @PutMapping
    @ApiOperation(value = "修改用户")
    public OutCome<User> updateUser(@RequestBody @ApiParam(value = "用户修改参数") User user) {
        return OutCome.success(userService.updateUser(user));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除用户")
    public OutCome deleteUser(@PathVariable @ApiParam(value = "用户id") Long id) {
        userService.deleteUser(id);
        return OutCome.success().setMessage("删除成功");
    }

    @PostMapping(value = "/upload-file")
    @ApiOperation(value = "上传用户图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "multipartFile", value = "文件流对象,接收数组格式", required = true, dataType = "__File")

    })
    public OutCome uploadFile(Long id, @RequestParam(value = "multipartFile") MultipartFile multipartFile) {
        userService.uploadFile(id, multipartFile);
        return OutCome.success().setMessage("上传成功");
    }

    @ApiOperation(value = "预览图片")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataTypeClass = Long.class)
    @GetMapping(value = "/preview-image", produces = {"image/jpg", "image/png", "image/jpeg"})
    public void previewImage(Long id, HttpServletResponse response) {
        userService.previewImage(id, response);
    }
}
