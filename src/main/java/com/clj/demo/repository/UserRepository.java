package com.clj.demo.repository;

import com.clj.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author lujia chen
 * @Created 2020/12/21
 * @Description
 * @date 2020/12/21
 * @Version 1.0.version
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long>, org.springframework.data.repository.Repository<User, Long> {
    /**
     * 根据用户名查询用户信息
     *
     * @param name -用户名
     * @return
     */
    List<User> findUserByName(String name);

    /**
     * 设置静态方法
     *
     * @return
     */
    static Long getCount() {
        return 10L;
    }

    /**
     * 用户登录
     *
     * @param username -用户名
     * @param password -密码
     * @return
     */
    @Query("select u from User u where u.name=?1 and u.password=?2")
    User login(@Param("username") String username, @Param("password") String password);
}
