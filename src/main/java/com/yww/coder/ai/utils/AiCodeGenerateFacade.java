package com.yww.coder.ai.utils;

import java.io.File;

import org.springframework.stereotype.Service;

import com.yww.coder.ai.model.HtmlCodeResult;
import com.yww.coder.ai.model.MultiFileCodeResult;
import com.yww.coder.ai.model.enums.CodeGenerateTypeEnum;
import com.yww.coder.ai.service.AiCodeGenerateService;
import com.yww.coder.ai.utils.parser.CodeParserExecutor;
import com.yww.coder.ai.utils.saver.CodeFileSaverExecutor;
import com.yww.coder.common.enums.base.InvalidContentSubStatusEnum;
import com.yww.coder.common.result.InvalidContentException;

import dev.langchain4j.model.output.structured.Description;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Description("统一的执行类")
@Slf4j
public class AiCodeGenerateFacade {

    @Resource
    private AiCodeGenerateService aiCodeGenerateService;


    /**
     * 统一入口：根据类型生成并保存代码
     *
     * @param userMessage     用户提示词
     * @param codeGenTypeEnum 生成类型
     * @return 保存的目录
     */
    public File generateAndSaveCode(String userMessage, CodeGenerateTypeEnum codeGenTypeEnum, Long appId) {
        if (codeGenTypeEnum == null) {
            throw new InvalidContentException(InvalidContentSubStatusEnum.PARAMS_ERROR, "生成类型为空");
        }
        return switch (codeGenTypeEnum) {
            case HTML -> {
                HtmlCodeResult result = aiCodeGenerateService.generateHtmlCode(userMessage);
                yield CodeFileSaverExecutor.executeSaver(result, CodeGenerateTypeEnum.HTML, appId);
            }
            case MULTI_FILE -> {
                MultiFileCodeResult result = aiCodeGenerateService.generateMultiFileCode(userMessage);
                yield CodeFileSaverExecutor.executeSaver(result, CodeGenerateTypeEnum.MULTI_FILE, appId);
            }
        };
    }

    /**
     * 统一入口：根据类型生成并保存代码（流式）
     *
     * @param userMessage     用户提示词
     * @param codeGenTypeEnum 生成类型
     */
    public Flux<String> generateAndSaveCodeStream(String userMessage, CodeGenerateTypeEnum codeGenTypeEnum, Long appId) {
        if (codeGenTypeEnum == null) {
            throw new InvalidContentException(InvalidContentSubStatusEnum.PARAMS_ERROR, "生成类型为空");
        }
        return switch (codeGenTypeEnum) {
            case HTML -> {
                Flux<String> codeStream = aiCodeGenerateService.generateHtmlCodeStream(userMessage);
                yield processCodeStream(codeStream, CodeGenerateTypeEnum.HTML, appId);
            }
            case MULTI_FILE -> {
                Flux<String> codeStream = aiCodeGenerateService.generateMultiFileCodeStream(userMessage);
                yield processCodeStream(codeStream, CodeGenerateTypeEnum.MULTI_FILE, appId);
            }
        };
    }


    /**
     * 通用流式代码处理方法
     *
     * @param codeStream  代码流
     * @param codeGenType 代码生成类型
     * @return 流式响应
     */
    private Flux<String> processCodeStream(Flux<String> codeStream, CodeGenerateTypeEnum codeGenType, Long appId) {
        StringBuilder codeBuilder = new StringBuilder();
        return codeStream.doOnNext(chunk -> {
            // 实时收集代码片段
            codeBuilder.append(chunk);
        }).doOnComplete(() -> {
            // 流式返回完成后保存代码
            try {
                String completeCode = codeBuilder.toString();
                // 使用执行器解析代码
                Object parsedResult = CodeParserExecutor.executeParser(completeCode, codeGenType);
                // 使用执行器保存代码
                File savedDir = CodeFileSaverExecutor.executeSaver(parsedResult, codeGenType, appId);
                log.info("保存成功，路径为：" + savedDir.getAbsolutePath());
            } catch (Exception e) {
                log.error("保存失败: {}", e.getMessage());
            }
        });
    }


}
