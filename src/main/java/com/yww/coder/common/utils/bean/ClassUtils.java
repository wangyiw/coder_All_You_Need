package com.yww.coder.common.utils.bean;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类的工具类
 */
public class ClassUtils {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    /**
     * 判断类中每个字段是否为空，有为空就返回false
     * 
     * @param obj 对象
     * @return
     */
    public static boolean areAllFieldsNonNull(Object obj) {
        if (obj == null) {
            return false; // 对象为null时，返回false
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (value == null) {
                    return false; // 如果有字段为null，返回false
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // 处理异常
                logger.error(e.getMessage());
            }
        }

        return true; // 所有字段都非null，返回true
    }

    /**
     * 判断列表中每个对象的每个属性是否为空
     * 
     * @param list 列表
     * @return
     * @param <T>
     */
    public static <T> boolean areAllFieldsNonNullInList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return false; // 列表为null或空时，返回false
        }

        for (T obj : list) {
            if (!areAllFieldsNonNull(obj)) {
                return false; // 如果列表中任何一个对象有字段为null，返回false
            }
        }

        return true; // 所有对象的所有字段都非null，返回true
    }

}
