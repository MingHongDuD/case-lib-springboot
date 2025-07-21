package com.damon.error;

import lombok.Data;

@Data
public class ErrorObjectWrapper {

    private ErrorObject error;

    public ErrorObjectWrapper(ErrorObject error, String appId){
        this.error = error;
        this.error.setSource(appId);
    }


}
