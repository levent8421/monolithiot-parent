package com.monolithiot.iot.templates.service;

import com.monolithiot.iot.templates.entity.MeasureData;
import org.springframework.data.domain.Page;

/**
 * Create by 郭文梁 2019/8/20 16:07
 * MeasureDataService
 * 测量数据相关业务行为定义组件
 *
 * @author 郭文梁
 * @data 2019/8/20 16:07
 */
public interface MeasureDataService {
    /**
     * 保存测量数据
     *
     * @param measureData 测量数据对象
     * @return 保存结果
     */
    MeasureData save(MeasureData measureData);

    /**
     * 查询一条测量数据 不存在时抛出异常
     *
     * @param id ID
     * @return 测量数据
     */
    MeasureData require(String id);

    /**
     * 通过用户ID获得测量数据列表
     *
     * @param userId 用户ID
     * @param page   页码
     * @param rows   煤业大小
     * @return Page Object
     */
    Page<MeasureData> findByUserId(Integer userId, int page, int rows);
}
