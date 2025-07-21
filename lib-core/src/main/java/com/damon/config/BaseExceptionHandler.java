package com.damon.config;

import com.damon.error.ErrorObjectWrapper;
import com.damon.error.ExceptionErrorObject;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.RemoteException;

/**
 * 全局异常管理类
 *
 * @author damon du/minghongdud
 */
@ControllerAdvice
@ResponseBody
public class BaseExceptionHandler {

    @Value("${spring.application.name}")
    protected String appId;

    // 创建 SLF4J Logger 实例，用于记录本身的日志
    private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorObjectWrapper> exceptionHandler500(HttpServletRequest request, Exception ex) {
        ResponseEntity var4;
        try{
            ResponseEntity<ErrorObjectWrapper> result = ResponseEntity.status(this.getUnexpectedErrorResponseCode(ex))
                    .body(new ErrorObjectWrapper(new ExceptionErrorObject("GENERAL_UNEXPECTED_EXCEPTION", (String) null, ex),this.appId));
            var4 = result;
        }finally {
            this.onUnexpectedError(request, ex);
        }

        return var4;
    }

//    protected final ResponseEntity<String> handleRemoteException(HttpServletRequest request, Exception ex){
//        ResponseEntity var4;
//        try{
//            ResponseEntity remote =( (RemoteExceptionWr)ex)
//        }finally {
//            this.onUnexpectedError(request, ex);
//        }
//
//        return var4;
//    }

    protected int getUnexpectedErrorResponseCode(Exception ex){
        return 500;
    }

    protected int getExpectedErrorResponseCode(Exception ex){
        return 403;
    }

    protected void onUnexpectedError(HttpServletRequest request, Exception ex){
        request.setAttribute("unexpected_exception", ex);
    }

    protected void getExpectedErrorResponseCode(HttpServletRequest request, Exception ex){
        request.setAttribute("expected_exception", ex);
    }
}
