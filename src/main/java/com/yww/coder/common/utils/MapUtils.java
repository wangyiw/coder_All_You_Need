package com.yww.coder.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Map集合工具类
 *
 * @author IT程
 * @date 2023/7/21 11:34
 */
public class MapUtils {
    /**
     * list转为map
     *
     * @param sourceList 源数据
     * @param getKey     获取map的key的方法
     * @param <K>        键类型
     * @param <V>        值类型
     * @return map数据
     */
    public static <K, V> Map<K, V> listToMap(List<V> sourceList, Function<V, K> getKey) {
        Map<K, V> map = new HashMap<>(16);
        for (V v : sourceList) {
            K key = getKey.apply(v);
            map.put(key, v);
        }
        return map;
    }

    /**
     * list转为mapList
     *
     * @param sourceList 源数据
     * @param getKey     获取map的key的方法
     * @param <K>        键类型
     * @param <V>        值类型
     * @return map数据
     */
    public static <K, V> Map<K, List<V>> listToMapList(List<V> sourceList, Function<V, K> getKey) {
        Map<K, List<V>> map = new HashMap<>(16);
        for (V v : sourceList) {
            K key = getKey.apply(v);
            if (map.containsKey(key)) {
                List<V> valueList = map.get(key);
                valueList.add(v);
            } else {
                List<V> valueList = new ArrayList<>();
                valueList.add(v);
                map.put(key, valueList);
            }
        }
        return map;
    }
}
