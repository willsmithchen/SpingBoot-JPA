package com.clj.demo.service;

import com.clj.demo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author lujia chen
 * @Created 2020/12/21
 * @Description
 * @date 2020/12/21
 * @Version 1.0.version
 **/
public interface UserService {
    /**
     * 查询所有用户信息
     *
     * @return 所有用户信息
     */
    List<User> findUsers();

    /**
     * 新增用户信息
     *
     * @param user -新增用户信息
     * @return
     */
    User createUser(User user);

    /**
     * 测试新增用户信息
     */
    void saveUser(User user);

    /**
     * 修改用户信息
     *
     * @param user -修改用户信息
     * @return
     */
    User updateUser(User user);

    /**
     * 删除用户信息
     *
     * @param id -用户id
     */
    Boolean deleteUser(Long id);

    /**
     * 根据用户id查询用户信息
     *
     * @param id -用户id
     * @return
     */
    User findUserById(Long id);

    /**
     * 上传图片
     *
     * @param multipartFile -图片文件
     */
    void uploadFile(Long id, MultipartFile multipartFile);

    /**
     * 图片预览
     *
     * @param id       -用户id
     * @param response -请求响应
     */
    void previewImage(Long id, HttpServletResponse response);

    /**
     * 根据用户名查询信息
     *
     * @param userName
     * @return
     */
    List<User> findUserByUserName(String userName);

    /**
     * 注册用户
     *
     * @param user -新增用户信息
     * @return
     */
    Boolean registUser(User user);

    /**
     * 修改用户信息
     *
     * @param user -修改用户信息
     * @return
     */
    Boolean modifyUser(User user);

    /**
     * 获取相应数量
     *
     * @return
     */
    Long getCount();

    /**
     * 用户登录
     *
     * @param username -用户名
     * @param password -密码
     * @return 是否成功
     */
    User login(String username, String password);

    /**
     * 注册用户信息
     *
     * @param user -用户信息
     * @param file -文件流
     * @return
     */
    Boolean register(User user, MultipartFile file);
}
