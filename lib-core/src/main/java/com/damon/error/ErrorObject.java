package com.damon.error;

public abstract class ErrorObject<T> {

    private String code;

    private String type;

    private String message;

    private String source;

    private T detail;

    public ErrorObject(String code, String type, String message, String source, T detail) {
        this.code = code;
        this.type = type;
        this.message = message;
        this.source = source;
        this.detail = detail;
    }

    public ErrorObject(String code, String type, String message, T details) {
        this(code, type, message, (String) null, details);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }
}
