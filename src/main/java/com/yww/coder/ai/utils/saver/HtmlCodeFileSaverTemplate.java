package com.yww.coder.ai.utils.saver;

import cn.hutool.core.util.StrUtil;
import com.yww.coder.ai.model.HtmlCodeResult;
import com.yww.coder.ai.model.enums.CodeGenerateTypeEnum;
import com.yww.coder.common.enums.base.InvalidContentSubStatusEnum;
import com.yww.coder.common.result.InvalidContentException;

/**
 * HTML代码文件保存器
 */
public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult> {

    @Override
    protected CodeGenerateTypeEnum getCodeType() {
        return CodeGenerateTypeEnum.HTML;
    }

    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
    }

    @Override
    protected void validateInput(HtmlCodeResult result) {
        super.validateInput(result);
        // HTML 代码不能为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new InvalidContentException(InvalidContentSubStatusEnum.INVALID_CONTENT, "HTML 代码不能为空");
        }
    }
}
