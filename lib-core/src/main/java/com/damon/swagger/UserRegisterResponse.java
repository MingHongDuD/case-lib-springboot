package com.damon.swagger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRegisterResponse {

    @JsonProperty("success")
    private Boolean success = null;

    @JsonProperty("message")
    private String message = null;

    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private CommonError error = null;
}
