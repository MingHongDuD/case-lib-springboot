package com.damon.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-07T06:41:39.097348751Z[GMT]")

/**
 * 用户注册请求-DTO
 *
 * @author damon du/minghongdud
 */
@Data
public class UserRegisterRequest {
    
    @NotEmpty(message = "主键不能为空")
    private String id;

    /**
     * 用户姓名
     */
    @JsonProperty("name")
    private String name;

    /**
     * 用户账号
     */
    @JsonProperty("account")
    private String account;

    /**
     * 用户密码
     */
    @JsonProperty("password")
    private String password;

    /**
     * 用户二次确认密码
     */
    @JsonProperty("checkPassword")
    private String checkPassword;

    /**
     * 用户邮箱
     */
    @JsonProperty("email")
    private String email;

}
