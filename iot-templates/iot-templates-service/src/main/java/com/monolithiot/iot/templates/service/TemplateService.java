package com.monolithiot.iot.templates.service;

import com.monolithiot.iot.templates.entity.MeasureTemplate;

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
}
