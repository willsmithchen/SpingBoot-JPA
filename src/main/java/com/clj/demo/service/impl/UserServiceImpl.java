package com.clj.demo.service.impl;

import com.clj.demo.entity.User;
import com.clj.demo.repository.UserRepository;
import com.clj.demo.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

/**
 * @Author lujia chen
 * @Created 2020/12/21
 * @Description 用户实现类
 * @date 2020/12/21
 * @Version 1.0.version
 **/
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserRepository userRepository;

    @Override
    public List<User> findUsers() {

        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("不存在的id" + id));
//        List<String> userNames=new ArrayList<>();
//        userNames.add("李三");
//        User userByUserName = userRepository.findUserByUserName(userNames);
        return user;
    }

    @Override
    public void uploadFile(Long id, MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            byte[] imageBytes = new byte[inputStream.available()];
            User user = findUserById(id);
            user.setImg(imageBytes);
            inputStream.read(imageBytes);
            updateUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void previewImage(Long id, HttpServletResponse response) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            User user = findUserById(id);
            byte[] image = user.getImg();
            response.setContentType("image/*,charset=utf-8");
            outputStream = response.getOutputStream();
            inputStream = new ByteArrayInputStream(image);
            int len;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("预览图片失败" + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
            }
        }


    }

    @Override
    public List<User> findUserByUserName(String userName) {
        return userRepository.findUserByName(userName);
    }

    @Override
    public Boolean registUser(User user) {
        createUser(user);
        return true;
    }

    @Override
    public Boolean modifyUser(User user) {
        updateUser(user);
        return true;
    }

    @Override
    public Long getCount() {
        return UserRepository.getCount();
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.login(username, password);
        if (Objects.isNull(user)) {
            return null;
        }
        return user;
    }

    @SneakyThrows
    @Override
    public Boolean register(User user, MultipartFile file) {
        InputStream inputStream = file.getInputStream();
        byte[] imageBytes = new byte[inputStream.available()];
        user.setImg(imageBytes);
        return registUser(user);
    }


}
