package com.ky.ulearning.spi.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author luyuhao
 * @date 2019/12/7 10:44
 */
@Data
@ApiModel("登录实体类")
public class LoginUser {
    @ApiModelProperty(value = "账号:学号/工号", required = true)
    private String username;

    @ApiModelProperty(value = "密码", example = "123456", required = true)
    private String password;

    @ApiModelProperty(value = "用户输入的验证码", example = "x6hf", required = true)
    private String code;

    @ApiModelProperty(value = "验证码生成的uuid", example = "55ac0bcf1f1044c69cd155c6393010ee", required = true)
    private String uuid = "";

    @ApiModelProperty(value = "登录类型 1：后台，2：教师 3：学生", required = true)
    private Integer loginType;

    @Override
    public String toString() {
        return "{username=" + username + ", password= ******}";
    }
}
