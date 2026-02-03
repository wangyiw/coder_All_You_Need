package com.yww.coder.genapp.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AppAdminUpdateRequestDto implements Serializable{

    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用封面
     */
    private String cover;

    /**
     * 优先级
     */
    private Integer priority;

    private static final long serialVersionUID = 1L;
}


