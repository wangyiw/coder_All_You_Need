package com.yww.coder.common.result;

import lombok.Getter;

import java.io.Serializable;

/**
 * 返回结果封装
 */
@Getter
public class BaseResponse<T> implements Serializable {

    /**
     * 主状态码
     * 数据由 StatusEnum 指定
     * 0 表示成功
     * 1 表示失败
     */
    private Integer code;

    /**
     * 主状态描述
     */
    private String message;

    /**
     * 可选
     * 目标数据
     */
    private T data = null;

    public BaseResponse() {
    }

    public BaseResponse(Integer code) {
        this.code = code;
        this.message = "";
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}