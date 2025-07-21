package com.damon.swagger;

import lombok.Data;
import lombok.Generated;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Data
public class CommonError {

    private String errorCode =null;

    private String errorMessage =null;

    private List<String> parameters =null;
}
