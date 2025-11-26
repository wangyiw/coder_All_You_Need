package com.yww.coder.core.result;

import java.io.Serializable;

/**
 * 返回结果封装
 */
public class BaseResponse<T> implements Serializable {

    /**
     * 主状态码
     * 数据由 StatusEnum 指定
     * 0 表示成功
     * 1 表示失败
     */
    private Integer statusCode;

    /**
     * 主状态描述
     */
    private String statusMsg;

    /**
     * 子状态码
     * 状态由AbnormalEquipmentSubStatusEnum、InvalidContentSubStatusEnum、InvalidOperationSubStatusEnum、SuccessSubStatusEnum。枚举指定
     */
    private Integer subStatusCode;

    /**
     * 子状态描述
     */
    private String subStatusMsg;

    /**
     * 可选
     * 详细描述
     * 用于详细的描述错误信息
     */
    private String details;

    /**
     * 可选
     * 目标数据
     */
    private T data = null;

    public BaseResponse() {
    }

    public BaseResponse(Integer statusCode) {
        this.statusCode = statusCode;
        this.statusMsg = "";
    }

    public BaseResponse(Integer statusCode, String statusMsg, Integer subStatusCode, String subStatusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.subStatusCode = subStatusCode;
        this.subStatusMsg = subStatusMsg;
    }

    public BaseResponse(Integer statusCode, String statusMsg, Integer subStatusCode, String subStatusMsg, T data) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.subStatusCode = subStatusCode;
        this.subStatusMsg = subStatusMsg;
        this.data = data;
    }

    public BaseResponse(Integer statusCode, String statusMsg, Integer subStatusCode, String subStatusMsg,
            String details) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.subStatusCode = subStatusCode;
        this.subStatusMsg = subStatusMsg;
        this.details = details;
    }

    public BaseResponse(Integer statusCode, String statusMsg, Integer subStatusCode, String subStatusMsg,
            String details,
            T data) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.subStatusCode = subStatusCode;
        this.subStatusMsg = subStatusMsg;
        this.details = details;
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public Integer getSubStatusCode() {
        return subStatusCode;
    }

    public String getSubStatusMsg() {
        return subStatusMsg;
    }

    public String getDetails() {
        return details;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "statusCode=" + statusCode +
                ", statusMsg='" + statusMsg + '\'' +
                ", subStatusCode=" + subStatusCode +
                ", subStatusMsg='" + subStatusMsg + '\'' +
                ", details='" + details + '\'' +
                ", data=" + data +
                '}';
    }
}
