package org.example.htmldesgin.utils;


import java.io.Serializable;

public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result(final Integer code, final String msg, final Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(200, "success", null);
    }

    public static Result success(final String msg, final Object data) {
        return new Result(200, msg, data);
    }

    public static Result success(final Object data) {
        return new Result(200, "success", data);
    }

    public static Result error() {
        return new Result(0, "error", null);
    }

    public static Result error(final String msg) {
        return new Result(0, msg, null);
    }

    public static Result error(final Object data) {
        return new Result(0, "", data);
    }

    public static Result error(final String msg, final Object data) {
        return new Result(0, msg, data);
    }
}

