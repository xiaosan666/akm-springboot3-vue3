package com.akm.springboot3.core.domain;

import com.akm.springboot3.core.utils.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Schema(title = "分页查询参数")
@Getter
@Setter
public class PageQuery<T> {
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Schema(title = "当前页", example = "1")
    private Integer pageNum = DEFAULT_PAGE_NUM;

    @Schema(title = "每页的数量", example = "10")
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    @Schema(title = "排序参数，形如:createTime desc,name asc", example = "1")
    private String orderBy;

    @Schema(title = "其他查询参数")
    private T condition;

    /**
     * 将排序字段 驼峰命名转下划线命名
     *
     * @param orderBy
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = StringUtils.hump2underline(orderBy, false);
    }
}
