package com.tdu.develop.common.exception;

public class JsonResult {
    private static final int SUCCESS = 1;
    private static final int ERROR = 0;
    private int state;
    private String message;
    private Object data;

    public JsonResult() {
        this.state = SUCCESS;
        this.message = "OK";
    }

    public JsonResult(Object data) {
        this();
        this.data = data;
    }

    public JsonResult(Throwable t) {
        this.state = ERROR;
        this.message = t.getMessage();
    }

    public Object getData() {
        return data;
    }

    public int getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }
}
