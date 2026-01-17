package com.yww.coder.common.utils.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yww.coder.common.result.InvalidOperationException;

/**
 * jackson进行树型结构转换工具类
 *
 * @author : 未见清海
 */
public class JacksonTreeUtil {

    private static final ObjectMapper objectMapper = JacksonUtils.getObjectMapper();

    /**
     * 把列表数据转为树形列表
     *
     * @param list       列表数据
     * @param idField    id字段
     * @param pidField   pid字段
     * @param childField 子元素字段
     * @param clazz      转换类
     * @param <T>        泛型
     * @return 树形结构
     */
    public static <T> List<T> listToTree(List<?> list, String idField, String pidField, String childField,
            Class<T> clazz) {
        List<Map<String, Object>> returnList = listFormatToTree(list, idField, pidField, childField);
        String returnString = JacksonUtils.objectToString(returnList);
        return JacksonUtils.stringToList(returnString, clazz);
    }

    /**
     * 把列表数据转为树形列表,并筛选数据,过滤无用节点
     *
     * @param selectList 需要检索出来的数组
     * @param list       列表数据
     * @param idField    id字段
     * @param pidField   pid字段
     * @param childField 子元素字段
     * @param clazz      转换类
     * @param <T>        泛型
     * @return 树形结构
     */
    public static <T> List<T> treeListLikeSelect(List<?> selectList, List<?> list, String idField, String pidField,
            String childField, Class<T> clazz) {
        List<Map<String, Object>> returnList = listFormatToTree(list, idField, pidField, childField);
        HashMap<String, Object> selectHash = new HashMap<>();
        // 将数组转为Object的形式，key为数组中的id
        for (Object o : selectList) {
            HashMap<String, Object> hashMap = MapListUtils.objectToMap(o);
            selectHash.put(hashMap.get(idField).toString(), hashMap);
        }
        // 筛选数据
        filterData(selectHash, returnList, childField, idField);
        // 转为所需要的类型
        String returnString = JacksonUtils.objectToString(returnList);
        return JacksonUtils.stringToList(returnString, clazz);
    }

    /**
     * 筛选列表数据的子节点是否存在需要的，如果不需要，移除该节点
     *
     * @param selectHash 需要筛选出来的列表数据
     * @param mapList    被筛选的列表数据
     * @param idField    id字段
     * @param childField 子元素字段
     */
    private static void filterData(HashMap<String, Object> selectHash, List<Map<String, Object>> mapList,
            String childField, String idField) {
        ListIterator<Map<String, Object>> iterator = mapList.listIterator();
        // 遍历所有数组节点
        while (iterator.hasNext()) {
            Map<String, Object> aVal = iterator.next();
            // 判断是否存在子节点
            List<Map<String, Object>> maps = MapListUtils.objectToMapList(aVal.get(childField));
            if (aVal.get(childField) != null) {
                filterData(selectHash, maps, childField, idField);
            }
            // 如果没有子节点的
            if (CollectionUtils.isEmpty(maps)) {
                aVal.remove(childField);
                if (selectHash.get(aVal.get(idField).toString()) == null) {
                    iterator.remove();
                }
            } else {
                // 重新赋值子节点
                aVal.put(childField, maps);
            }
        }
    }

    /**
     * 把列表数据格式化为树形列表
     *
     * @param list       列表数据
     * @param idField    id字段
     * @param pidField   pid字段
     * @param childField 子元素字段
     * @return 树形结构（JSONArray类型）
     */
    private static List<Map<String, Object>> listFormatToTree(List<?> list, String idField, String pidField,
            String childField) {
        // 将对象集合读成数组json
        String listJson = JacksonUtils.objectToString(list);
        List<Map<String, Object>> array;
        try {
            array = objectMapper.readValue(listJson,
                    objectMapper.getTypeFactory().constructParametricType(List.class, Map.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // throw new
            // InvalidOperationException(InvalidOperationSubStatusEnum.JSON_TRANSFORMATION_FAIL,
            // "Jackson json格式转换异常");
            throw new InvalidOperationException("Jackson json格式转换异常");
        }

        List<Map<String, Object>> returnList = new ArrayList<>();
        Map<String, Map<String, Object>> hash = new HashMap<>();
        for (Map<String, Object> o : array) {
            hash.put(o.get(idField).toString(), o);
        }
        for (Map<String, Object> map : array) {
            // 在hash中取出key为单条记录中pid的值
            Map<String, Object> hashVp = hash.get(map.get(pidField).toString());
            // 如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
            if (hashVp != null) {
                // 检查是否有child属性
                Object obj = hashVp.get(childField);
                List<Object> result = MapListUtils.objectToList(obj);
                result.add(map);
                hashVp.put(childField, result);
            } else {
                returnList.add(map);
            }
        }
        return returnList;
    }

    /**
     * 根据指定的父id获取其下的树结构
     *
     * @param list       列表数据
     * @param parentId   父id
     * @param idField    id字段名称
     * @param pidField   pid字段名称
     * @param childField 子元素字段名称
     * @param clazz      转换的目标类
     * @param <T>        泛型类型
     * @return 指定父id下的树结构列表
     */
    public static <T> List<T> getSubTree(List<?> list, String parentId, String idField, String pidField,
            String childField, Class<T> clazz) {
        // 将列表格式化为树形结构
        List<Map<String, Object>> fullTree = listFormatToTree(list, idField, pidField, childField);
        List<Map<String, Object>> subTreeList = new ArrayList<>();

        // 遍历树形结构，查找指定的父节点
        for (Map<String, Object> node : fullTree) {
            if (Objects.equals(node.get(idField).toString(), parentId)) {
                subTreeList.add(node);
                break;
            } else {
                // 递归查找子树
                Map<String, Object> found = findSubTree(node, parentId, idField, childField);
                if (found != null) {
                    subTreeList.add(found);
                    break;
                }
            }
        }

        // 如果未找到指定的父id，则返回空列表
        if (subTreeList.isEmpty()) {
            return Collections.emptyList();
        }

        // 将子树转换为目标类型列表
        String returnString = JacksonUtils.objectToString(subTreeList);
        return JacksonUtils.stringToList(returnString, clazz);
    }

    /**
     * 递归查找指定父id的子树
     *
     * @param node       当前节点
     * @param parentId   需要查找的父id
     * @param idField    id字段名称
     * @param childField 子元素字段名称
     * @return 找到的子树节点，如果未找到则返回null
     */
    private static Map<String, Object> findSubTree(Map<String, Object> node, String parentId, String idField,
            String childField) {
        if (node.get(childField) != null) {
            List<Map<String, Object>> children = MapListUtils.objectToMapList(node.get(childField));
            for (Map<String, Object> child : children) {
                if (Objects.equals(child.get(idField).toString(), parentId)) {
                    return child;
                } else {
                    Map<String, Object> result = findSubTree(child, parentId, idField, childField);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取树形结构中指定字段的所有值
     *
     * @param tree       树形结构数据
     * @param fieldName  需要提取的字段名称
     * @param childField 子元素字段
     * @param <T>        泛型类型
     * @return 指定字段的所有值集合
     */
    public static <T> List<Object> getFieldValues(List<T> tree, String fieldName, String childField) {
        List<Object> fieldValues = new ArrayList<>();
        extractFieldValues(tree, fieldName, childField, fieldValues);
        return fieldValues;
    }

    /**
     * 递归遍历树形结构，提取指定字段的所有值
     *
     * @param nodeList   当前树形结构的节点列表
     * @param fieldName  需要提取的字段名称
     * @param childField 子元素字段名称
     * @param result     存储字段值的集合
     */
    private static <T> void extractFieldValues(List<T> nodeList, String fieldName, String childField,
            List<Object> result) {
        for (T node : nodeList) {
            // 使用反射来获取指定字段的值
            try {
                Object fieldValue = getFieldValue(node, fieldName);
                if (fieldValue != null) {
                    result.add(fieldValue);
                }

                // 如果存在子元素，递归提取子节点的字段值
                List<?> children = (List<?>) node.getClass().getMethod("get" + capitalize(childField)).invoke(node);
                if (children != null) {
                    extractFieldValues((List<T>) children, fieldName, childField, result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用反射获取指定字段的值
     *
     * @param obj       实体对象
     * @param fieldName 字段名称
     * @return 字段的值
     */
    private static Object getFieldValue(Object obj, String fieldName) {
        try {
            return obj.getClass().getMethod("get" + capitalize(fieldName)).invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 大写后的字符串
     */
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
