package com.damon.swagger;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotEmpty(message = "主键不能为空")
    private String id;

    private String name;
}
