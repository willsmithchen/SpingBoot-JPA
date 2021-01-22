package com.clj.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author lujia chen
 * @version 1.0.version
 * @created 2020/12/21
 * @description 用户类
 * @date 2020/12/21
 **/
@Entity
@Table(name = "user")
@Data
@ApiModel(value = "用户类")
@Accessors(chain = true)
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(name = "id", value = "用户id")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "用户名称", example = "小明")
    private String name;
    @Column(name = "password")
    @ApiModelProperty(name = "password", value = "用户密码", example = "admin123")
    private String password;
    @Column(name = "create_time")
    @ApiModelProperty(name = "createTime", value = "用户创建时间")
    private String createTime;
    @Column(name = "img")
    @ApiModelProperty(name = "img", value = "用户图片")
    private byte[] img;
    @Column(name = "user_type")
    @ApiModelProperty(name = "userType", value = "用户类型：0：普通用户，1：vip用户，2：超级用户，3：管理员；")
    private Integer userType;
    @Column(name = "del_flag")
    @ApiModelProperty(name = "delFlag", value = "用户信息删除标识")
    private Integer delFlag;

    public static String getNickName(String name) {
        return name;
    }

    public User() {
    }

    public User(String name) {
        this.name = name;
    }
}

