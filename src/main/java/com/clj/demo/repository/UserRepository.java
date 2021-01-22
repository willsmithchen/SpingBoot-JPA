package com.clj.demo.repository;

import com.clj.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
    User findUserByName(String name);

    /**
     * 设置静态方法
     *
     * @return
     */
    static Long getCount() {
        return 10L;
    }
}
