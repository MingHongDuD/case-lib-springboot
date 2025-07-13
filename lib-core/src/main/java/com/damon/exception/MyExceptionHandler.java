package com.damon.exception;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 25118
 */
@ControllerAdvice("com.damon")
public class MyExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    public void initWebBinder(WebDataBinder binder) {
        // 对日期统一处理
        binder.addCustomFormatter(new DateFormatter("YYYY-MM-DD"));
        // 添加数据校验
    }

    /**
     * 把值绑定到 MODEL 中，使全局 @RequestMapping 可以获取到该值
     */
    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("attribute", "The Attribute");
    }
    /**
     * 捕获CustomException
     *
     * @param e
     * @return json格式类型
     */
    @ResponseBody
    @ExceptionHandler({CustomException.class}) //指定拦截异常的类型
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //自定义浏览器返回状态码
    public Map<String, Object> customExceptionHandler(Exception e){
        Map<String , Object > map = new HashMap<>();
        map.put("code",e.getCause());
        map.put("msg", e.getMessage());
        return map;
    }


}
