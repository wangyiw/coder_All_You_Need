package com.yww.coder.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

/**
 * 分页查询结果封装
 */

public class PageResponseDto<T> implements Serializable {

    /**
     * 当前页数
     */
    private Integer current;

    /**
     * 每页条数
     */
    private Integer size;

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 数据集合
     */
    private List<T> list;

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getTotal() {
        return total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageResultVo{" +
                "current=" + current +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
