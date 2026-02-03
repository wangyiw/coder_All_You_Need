package com.yww.coder.genapp.model.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * 用户生成应用请求DTO
 */
@Data
public class GenerateAppRequestDto implements Serializable{
    /**
     * 应用初始化提示词
     */
    private String initPrompt;

    private static final long serialVersionUID = 1L;


}