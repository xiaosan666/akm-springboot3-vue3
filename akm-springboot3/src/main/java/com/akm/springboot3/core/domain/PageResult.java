package com.akm.springboot3.core.domain;

import com.github.pagehelper.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;


@Schema(title = "分页结果")
@Getter
public class PageResult<T> {
    @Schema(title = "总记录数")
    private long total;

    @Schema(title = "结果集")
    private List<T> list;

    public PageResult(List<T> list) {
        if (list instanceof Page<T> page) {
            this.total = page.getTotal();
            this.list = page;
        }
    }

    public PageResult() {
    }

}
