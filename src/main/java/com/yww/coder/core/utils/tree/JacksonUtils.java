package com.yww.coder.core.utils.tree;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * jackson的json处理工具类
 *
 * @author : 未见清海
 */
public class JacksonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 格式化时间格式
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        MAPPER.registerModule(new JavaTimeModule());
    }

    public static ObjectMapper getObjectMapper() {
        return MAPPER;
    }

    /**
     * 把对象转换为json字符串
     *
     * @param data 数据
     * @return json字符串
     */
    public static String objectToString(Object data) {
        try {
            if (data == null) {
                return null;
            }
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转为对象
     *
     * @param jsonString json字符串
     * @param beanType   转换类
     * @param <T>        泛型
     * @return 转换对象
     */
    public static <T> T stringToObject(String jsonString, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonString, beanType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转换为数组对象
     *
     * @param jsonString json字符串
     * @param beanType   转换类
     * @param <T>        泛型
     * @return 转换数组对象
     */
    public static <T> List<T> stringToList(String jsonString, Class<T> beanType) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(jsonString, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
