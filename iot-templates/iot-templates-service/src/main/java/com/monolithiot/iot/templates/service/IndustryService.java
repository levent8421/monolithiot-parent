package com.monolithiot.iot.templates.service;

import com.monolithiot.iot.service.basic.AbstractService;
import com.monolithiot.iot.templates.entity.Industry;

import java.util.List;

/**
 * Create By leven ont 2019/8/26 21:45
 * Class Name :[IndustryService]
 * <p>
 * 行业相关业务行为定义
 *
 * @author leven
 */
public interface IndustryService extends AbstractService<Industry> {
    /**
     * 查询所有行业 （排序）
     *
     * @return Industry List
     */
    List<Industry> findAllWithOrder();
}
