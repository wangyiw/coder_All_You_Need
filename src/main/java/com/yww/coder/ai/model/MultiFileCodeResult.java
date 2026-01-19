package com.yww.coder.ai.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
public class MultiFileCodeResult {

    @Description("生成的HTML代码")
    private String htmlCode;

    @Description("生成的CSS代码")
    private String cssCode;

    @Description("生成的JavaScript代码")
    private String jsCode;

    @Description("生成的代码的描述")
    private String description;
}

