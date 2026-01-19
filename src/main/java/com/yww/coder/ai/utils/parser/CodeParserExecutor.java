package com.yww.coder.ai.utils.parser;

import com.yww.coder.ai.model.enums.CodeGenerateTypeEnum;

/**
 * 代码执行器：策略上下文类
 * 根据枚举类型选择不同的解析器
 */
public class CodeParserExecutor {

    private static final HtmlCodeParser htmlCodeParser = new HtmlCodeParser();
    private static final MultiFileCodeParser multiFileCodeParser = new MultiFileCodeParser();

    public static Object executeParser(String codeContent, CodeGenerateTypeEnum codeGenerateTypeEnum) {
        if (codeGenerateTypeEnum == null) {
            return null;
        }
        return switch (codeGenerateTypeEnum) {
            case HTML -> htmlCodeParser.parseCode(codeContent);
            case MULTI_FILE -> multiFileCodeParser.parseCode(codeContent);
        };
    }
}
