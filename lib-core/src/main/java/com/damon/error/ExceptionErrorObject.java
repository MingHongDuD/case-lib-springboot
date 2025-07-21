package com.damon.error;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionErrorObject extends ErrorObject<String>{

    public static final String GENERAL_UNEXPECTED_EXCEPTION = "GENERAL_UNEXPECTED_EXCEPTION";

    public static final String GENERAL_EXPECTED_EXCEPTION = "GENERAL_EXPECTED_EXCEPTION";

    public ExceptionErrorObject(String code, String type, Exception e) {
        super(code, type, e.getMessage(), stackTraceToString(e));
    }

    public ExceptionErrorObject(String code, String type, String message, String details) {
        super(code, type, message, details);
    }

    private static String stackTraceToString(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
