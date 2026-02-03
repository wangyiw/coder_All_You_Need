package com.yww.coder.genapp.model.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * 更新应用请求DTO
 */
@Data
public class UpdateAppRequestDto implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    private static final long serialVersionUID = 1L;
}

