package com.yww.coder.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 分页查询入参基础实体
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class PageRequestDto {

    /**
     * 当前页 ,分页使用
     */
    @Getter
    @Schema(description = "当前页")
    private Integer current;

    /**
     * 每页条数 ,分页使用
     */
    @Getter
    @Schema(description = "每页条数")
    private Integer size;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认降序）
     */
    private String sortOrder = "descend";

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getOffset() {
        return (current - 1) * size;
    }

    @Override
    public String toString() {
        return "PageQueryDto{" +
                "current=" + current +
                ", size=" + size +
                "} " + super.toString();
    }
}
