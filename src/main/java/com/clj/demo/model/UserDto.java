package com.clj.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lujia chen
 * @Created 2021/2/25
 * @Description 用户参数
 * @date 2021/2/25
 * @Version 1.0.version
 **/
@Data
public class UserDto implements Cloneable {

    @ApiModelProperty(name = "id", value = "用户id")
    private Long id;
    @ApiModelProperty(name = "name", value = "用户名称", example = "小明")
    private String name;
    @ApiModelProperty(name = "password", value = "用户密码", example = "admin123")
    private String password;
    @ApiModelProperty(name = "createTime", value = "用户创建时间")
    private String createTime;
    @ApiModelProperty(name = "img", value = "用户图片")
    private byte[] img;
    @ApiModelProperty(name = "userType", value = "用户类型：0：普通用户，1：vip用户，2：超级用户，3：管理员；")
    private Integer userType;
    @ApiModelProperty(name = "delFlag", value = "用户信息删除标识")
    private Integer delFlag;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
