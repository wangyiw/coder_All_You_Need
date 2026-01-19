package com.yww.coder.ai;

import com.yww.coder.ai.model.HtmlCodeResult;
import com.yww.coder.ai.model.MultiFileCodeResult;
import com.yww.coder.ai.service.AiCodeGenerateService;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
    dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration.class
})
class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGenerateService aiCodeGenerateService;

    @Test
    void generateHtmlCode() {
        HtmlCodeResult result = aiCodeGenerateService.generateHtmlCode("做一个yww个人的博客，不超过 20 行");
        Assertions.assertNotNull(result);
        System.out.println("generateHtmlCode result = " + result);
        Assertions.assertNotNull(result.getHtmlCode());
        Assertions.assertFalse(result.getHtmlCode().isBlank());
    }

    @Test
    void generateMultiFileCode() {
        MultiFileCodeResult result = aiCodeGenerateService.generateMultiFileCode("做个留言板");
        Assertions.assertNotNull(result);
        System.out.println("generateMultiFileCode result = " + result);
        Assertions.assertNotNull(result.getHtmlCode());
        Assertions.assertFalse(result.getHtmlCode().isBlank());
        Assertions.assertNotNull(result.getCssCode());
        Assertions.assertFalse(result.getCssCode().isBlank());
        Assertions.assertNotNull(result.getJsCode());
        Assertions.assertFalse(result.getJsCode().isBlank());
    }
    @Test
    void generateMultiFileCodeStream() {
        Flux<String> codeStream = aiCodeGenerateService.generateMultiFileCodeStream("实现一个yww的个人博客站，主要包括技术栈和最近的工作");
        Assertions.assertNotNull(codeStream);

        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(60), () -> StepVerifier.create(codeStream
                        .filter(chunk -> chunk != null && !chunk.isBlank())
                        .take(1))
                .expectNextMatches(chunk -> chunk != null && !chunk.isBlank())
                .verifyComplete());
    }

    // @Test
    // void testChatMemory() {
    //     HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("做个工具网站，总代码量不超过 20 行");
    //     Assertions.assertNotNull(result);
    //     result = aiCodeGeneratorService.generateHtmlCode("不要生成网站，告诉我你刚刚做了什么？");
    //     Assertions.assertNotNull(result);
    //     result = aiCodeGeneratorService.generateHtmlCode("做个yww工具网站，总代码量不超过 20 行");
    //     Assertions.assertNotNull(result);
    //     result = aiCodeGeneratorService.generateHtmlCode("不要生成网站，告诉我你刚刚做了什么？");
    //     Assertions.assertNotNull(result);
    // }
}
