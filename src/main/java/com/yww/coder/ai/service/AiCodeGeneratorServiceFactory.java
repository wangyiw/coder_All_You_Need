package com.yww.coder.ai.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yww.coder.ai.model.enums.CodeGenerateTypeEnum;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;

@Configuration
public class AiCodeGeneratorServiceFactory {
    @Resource
    private ChatModel chatModel;
    
    @Resource
    private StreamingChatModel streamingChatModel;

    @Bean
    public AiCodeGenerateService aiCodeGenerateService() {
        /**
         * 创建AI代码生成服务/流式生成
         * 使用AiServices.builder方法创建服务实例
         * 第一个参数是接口类型，第二个参数是ChatModel实例
         */
        return AiServices.builder(AiCodeGenerateService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .build();
    }

    public AiCodeGenerateService getAiCodeGenerateService(Long appId, CodeGenerateTypeEnum codeGenerateTypeEnum) {
        return aiCodeGenerateService();
    }
}