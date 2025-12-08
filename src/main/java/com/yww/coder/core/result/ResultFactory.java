package com.yww.coder.core.result;

import com.yww.coder.core.enums.base.StatusEnum;
import com.yww.coder.core.enums.base.SuccessSubStatusEnum;

/**
 * 返回对象封装工具类
 */
public class ResultFactory {

    /**
     * 获取成功结果
     */
    public static BaseResponse<?> getSuccessResult() {
        return new BaseResponse<>(StatusEnum.SUCCESS.getStatus(), StatusEnum.SUCCESS.getDescription());
    }

    /**
     * 获取成功结果（带消息）
     */
    public static BaseResponse<?> getSuccessResult(String message) {
        return new BaseResponse<>(StatusEnum.SUCCESS.getStatus(), message);
    }

    /**
     * 获取成功结果（带数据）
     */
    public static <T> BaseResponse<T> getSuccessResult(T data) {
        return new BaseResponse<>(StatusEnum.SUCCESS.getStatus(), StatusEnum.SUCCESS.getDescription(), data);
    }
    
    public static <T> BaseResponse<T> getSuccessResult(SuccessSubStatusEnum statusEnum, T data) {
    return new BaseResponse<>(StatusEnum.SUCCESS.getStatus(), statusEnum.getDescription(), data);
}

    /**
     * 获取成功结果（带消息和数据）
     */
    public static <T> BaseResponse<T> getSuccessResult(String message, T data) {
        return new BaseResponse<>(StatusEnum.SUCCESS.getStatus(), message, data);
    }


    /**
     * 获取失败结果
     */
    public static BaseResponse<?> getFailureResult(StatusEnum statusEnum) {
        return new BaseResponse<>(statusEnum.getStatus(), statusEnum.getDescription());
    }

    /**
     * 获取失败结果（带消息）
     */
    public static BaseResponse<?> getFailureResult(StatusEnum statusEnum, String message) {
        return new BaseResponse<>(statusEnum.getStatus(), message);
    }

    /**
     * 获取失败结果（带数据）
     */
    public static <T> BaseResponse<T> getFailureResult(StatusEnum statusEnum, T data) {
        return new BaseResponse<>(statusEnum.getStatus(), statusEnum.getDescription(), data);
    }

    /**
     * 获取失败结果（带消息和数据）
     */
    public static <T> BaseResponse<T> getFailureResult(StatusEnum statusEnum, String message, T data) {
        return new BaseResponse<>(statusEnum.getStatus(), message, data);
    }

    /**
     * 获取返回结果
     * 
     * @param statusEnum 状态枚举
     * @param message    消息描述
     * @return 返回结果
     */
    public static <T> BaseResponse<T> getResult(StatusEnum statusEnum, String message) {
        return new BaseResponse<>(statusEnum.getStatus(), message);
    }

    /**
     * 获取返回结果
     * 
     * @param statusEnum 状态枚举
     * @param message    消息描述
     * @param data       数据
     * @return 返回结果
     */
    public static <T> BaseResponse<T> getResult(StatusEnum statusEnum, String message, T data) {
        return new BaseResponse<>(statusEnum.getStatus(), message, data);
    }
}
