package com.monolithiot.iot.templates.Vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;

/**
 * Create by 郭文梁 2019/7/19 17:33
 * SimplePageRequest
 * 简单分页参数
 *
 * @author 郭文梁
 * @data 2019/7/19 17:33
 */
@Data
@NoArgsConstructor
public class SimplePageRequest implements Pageable {
    /**
     * 页码
     */
    private int pageNumber;
    /**
     * 每页大小
     */
    private int pageSize;

    public SimplePageRequest(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public long getOffset() {
        return (pageNumber - 1) * pageSize;
    }

    @Override
    @NotNull
    public Sort getSort() {
        return Sort.unsorted();
    }

    @Override
    public Pageable next() {
        return new SimplePageRequest(pageNumber + 1, pageSize);
    }

    @Override
    public Pageable previousOrFirst() {
        return first();
    }

    @Override
    public Pageable first() {
        return new SimplePageRequest(1, 10);
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
