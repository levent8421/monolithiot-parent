package com.monolithiot.iot.web.controller;

import javax.validation.constraints.NotNull;

/**
 * Create by 郭文梁 2019/5/18 0018 12:38
 * AbstractController
 * 控制器基类
 *
 * @author 郭文梁
 * @data 2019/5/18 0018
 */
public abstract class AbstractController {
    /**
     * 默认页码
     */
    private static final int DEFAULT_PAGE = 1;
    /**
     * 默认每页大小
     */
    private static final int DEFAULT_ROWS = 10;

    /**
     * 当传入的page=null时返回默认页码，否则返回page
     *
     * @param page 页码
     * @return 页码
     */
    @NotNull
    protected int defaultPage(Integer page) {
        return page == null ? DEFAULT_PAGE : page;
    }

    /**
     * 当传入的rows==null时返回默认每页大小，否则反回rows
     *
     * @param rows 每页大小
     * @return 每页大小
     */
    @NotNull
    protected int defaultRows(Integer rows) {
        return rows == null ? DEFAULT_ROWS : rows;
    }
}
