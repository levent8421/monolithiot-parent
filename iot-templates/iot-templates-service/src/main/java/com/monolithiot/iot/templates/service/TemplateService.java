package com.monolithiot.iot.templates.service;

import com.monolithiot.iot.templates.entity.MeasureTemplate;
import org.springframework.data.domain.Page;

/**
 * Create By leven ont 2019/7/18 22:52
 * Class Name :[TemplateService]
 * <p>
 * 模板相关业务行为定义
 *
 * @author leven
 */
public interface TemplateService {
    /**
     * 保存模板到仓库
     *
     * @param measureTemplate 模板
     * @return 保存结果 with id
     */
    MeasureTemplate save(MeasureTemplate measureTemplate);

    /**
     * 搜索模板
     *
     * @param q    搜索文本
     * @param page 页码
     * @param rows 每页大小
     * @return spring data page
     */
    Page<MeasureTemplate> search(String q, Integer page, Integer rows);
}
