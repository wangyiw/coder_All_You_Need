package com.yww.coder.core.result;

import com.yww.coder.core.enums.base.ResultSubStatus;
import com.yww.coder.core.enums.base.StatusEnum;
import com.yww.coder.core.enums.base.SuccessSubStatusEnum;

/**
 * 返回对象封装工具类
 */
public class ResultFactory {

    public static BaseResponse<?> getSuccessResult(SuccessSubStatusEnum successSubStatusAdvice) {

        return new BaseResponse<>(StatusEnum.SUCCESS.getStatus(), StatusEnum.SUCCESS.getDescription(),
                successSubStatusAdvice.getSubStatus(), successSubStatusAdvice.getDescription());
    }

    public static BaseResponse<?> getSuccessResult(SuccessSubStatusEnum successSubStatusAdvice, String details) {

        return new BaseResponse<>(StatusEnum.SUCCESS.getStatus(), StatusEnum.SUCCESS.getDescription(),
                successSubStatusAdvice.getSubStatus(), successSubStatusAdvice.getDescription(), details);
    }

    public static <T> BaseResponse<T> getSuccessResult(SuccessSubStatusEnum successSubStatusAdvice, T t) {

        return new BaseResponse<T>(StatusEnum.SUCCESS.getStatus(), StatusEnum.SUCCESS.getDescription(),
                successSubStatusAdvice.getSubStatus(), successSubStatusAdvice.getDescription(), t);
    }

    public static <T> BaseResponse<T> getSuccessResult(SuccessSubStatusEnum successSubStatusAdvice, String details,
            T t) {

        return new BaseResponse<T>(StatusEnum.SUCCESS.getStatus(), StatusEnum.SUCCESS.getDescription(),
                successSubStatusAdvice.getSubStatus(), successSubStatusAdvice.getDescription(), details, t);
    }

    public static BaseResponse<?> getFailureResult(StatusEnum statusEnum, ResultSubStatus resultSubStatus) {

        return new BaseResponse<>(statusEnum.getStatus(), statusEnum.getDescription(), resultSubStatus.getSubStatus(),
                resultSubStatus.getDescription());
    }

    public static <T> BaseResponse<T> getFailureResult(StatusEnum statusEnum, ResultSubStatus resultSubStatus, T t) {

        return new BaseResponse<T>(statusEnum.getStatus(), statusEnum.getDescription(), resultSubStatus.getSubStatus(),
                resultSubStatus.getDescription(), t);
    }

    public static BaseResponse<?> getFailureResult(StatusEnum statusEnum, ResultSubStatus resultSubStatus,
            String details) {

        return new BaseResponse<>(statusEnum.getStatus(), statusEnum.getDescription(), resultSubStatus.getSubStatus(),
                resultSubStatus.getDescription(), details);
    }

    public static <T> BaseResponse<T> getFailureResult(StatusEnum statusEnum, ResultSubStatus resultSubStatus,
            String details, T t) {

        return new BaseResponse<T>(statusEnum.getStatus(), statusEnum.getDescription(), resultSubStatus.getSubStatus(),
                resultSubStatus.getDescription(), details, t);
    }

    /**
     * 获取返回结果
     * 
     * @param statusEnum      主状态枚举
     * @param resultSubStatus 子状态枚举
     * @param details         详细描述
     * @return 返回结果
     */
    public static <T> BaseResponse<T> getResult(StatusEnum statusEnum, ResultSubStatus resultSubStatus,
            String details) {
        return new BaseResponse<T>(statusEnum.getStatus(), statusEnum.getDescription(), resultSubStatus.getSubStatus(),
                resultSubStatus.getDescription(), details);
    }
}
