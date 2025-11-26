package com.yww.coder.core.utils.page;

import java.util.List;

import com.yww.coder.core.dto.PageRequestDto;
import com.yww.coder.core.dto.PageResponseDto;
import com.yww.coder.core.utils.bean.BeanUtils;

/**
 * 分页工具类
 */
public class PageUtil {

    /**
     * 分页对象数据类型转换
     *
     * @param page   源分页对象
     * @param tClass 目标分页对象集合数据类型
     * @param <T>    目标数据类型
     * @param <E>    源数据类型
     * @return 目标分页对象
     */
    public static <T, E> PageResponseDto<T> pageDataTypeConversion(PageResponseDto<E> page, Class<T> tClass) {

        PageResponseDto<T> pageResult = new PageResponseDto<>();
        pageResult.setCurrent(page.getCurrent());
        pageResult.setSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        List<E> pageList = page.getList();

        List<T> tList = BeanUtils.convertToList(pageList, tClass);
        pageResult.setList(tList);
        return pageResult;
    }

    /**
     * 封装分页对象数据
     *
     * @return 目标分页对象
     */
    public static <T> PageResponseDto<T> toPage(PageRequestDto pageQueryDto, Integer total, List<T> tList) {

        PageResponseDto<T> pageResult = new PageResponseDto<>();
        pageResult.setCurrent(pageQueryDto.getCurrent());
        pageResult.setSize(pageQueryDto.getSize());
        pageResult.setTotal(total);
        pageResult.setList(tList);
        return pageResult;
    }

}
