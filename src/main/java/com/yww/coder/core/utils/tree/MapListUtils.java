package com.yww.coder.core.utils.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

/**
 * Map工具类
 *
 * @author : 未见清海
 */
public class MapListUtils {

    /**
     * object 转 map
     *
     * @param object 被转换的对象
     * @return map
     */
    public static HashMap<String, Object> objectToMap(Object object) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (object instanceof HashMap) {
            HashMap hashMap = (HashMap) object;
            for (Object key : hashMap.keySet()) {
                Object value = hashMap.get(key);
                resultMap.put(key.toString(), value);
            }
            return resultMap;
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object val = field.get(object);
                if (null == val) {
                    continue;
                }
                resultMap.put(field.getName(), val);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    /**
     * 把object对象转为数组<Map>
     *
     * @param object 被转换的object对象
     * @return 数组<Map>
     */
    public static List<Map<String, Object>> objectToMapList(Object object) {
        List<Object> objectList = objectToList(object);
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(objectList)) {
            for (Object o : objectList) {
                Map<String, Object> hashMap = objectToMap(o);
                resultList.add(hashMap);
            }
        }
        return resultList;
    }

    /**
     * object转为数组对象
     *
     * @param object 被转换的对象
     * @return 数组对象
     */
    public static List<Object> objectToList(Object object) {
        List<Object> result = new ArrayList<>();
        if (object != null) {
            if (object instanceof ArrayList<?>) {
                result.addAll((List<?>) object);
            }
        }
        return result;
    }
}
