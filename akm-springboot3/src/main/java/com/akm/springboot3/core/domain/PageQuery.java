package com.akm.springboot3.core.domain;

import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.exception.BusinessException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.regex.Pattern;


@Schema(title = "分页查询参数")
@Getter
@Setter
public class PageQuery<T> {
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final Pattern ORDER_BY_ITEM_PATTERN = Pattern.compile(
        "^([a-zA-Z][a-zA-Z0-9_]*\\.)?[a-zA-Z][a-zA-Z0-9_]*(\\s+(asc|desc))?$",
        Pattern.CASE_INSENSITIVE);

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
        if (StringUtils.isBlank(orderBy)) {
            this.orderBy = orderBy;
            return;
        }
        String formattedOrderBy = StringUtils.hump2underline(orderBy, false).trim();
        for (String orderByItem : formattedOrderBy.split(",", -1)) {
            if (!ORDER_BY_ITEM_PATTERN.matcher(orderByItem.trim().toLowerCase(Locale.ROOT)).matches()) {
                throw new BusinessException("排序参数不合法");
            }
        }
        this.orderBy = formattedOrderBy;
    }
}
