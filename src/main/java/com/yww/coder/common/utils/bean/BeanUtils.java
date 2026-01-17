package com.yww.coder.common.utils.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

/**
 * bean获取工具类
 */
public class BeanUtils {
    /**
     * the beanCopierMap
     */
    private static final ConcurrentMap<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();

    /**
     * 两个集合间的转换
     * 
     * @param list
     * @param clz
     * @param <T>
     * @return
     */
    public static <E, T> List<T> convertToList(List<E> list, Class<T> clz) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        } else {
            List<T> result = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                try {
                    Object next = var3.next();
                    T t = clz.getDeclaredConstructor().newInstance();
                    copyProperties(next, t);
                    result.add(t);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return result;
        }
    }

    /**
     * @description 两个类对象之间转换
     * @param source
     * @param target
     * @return
     * @return T
     */
    public static <T> T convert(Object source, Class<T> target) {
        T t = null;
        if (source == null) {
            return null;
        }
        try {
            t = target.getDeclaredConstructor().newInstance();
            copyProperties(source, t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 前者给后者赋值,自动忽略null值
     * 
     * @param source
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * 前者给后者赋值,自动忽略null值
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }
}
