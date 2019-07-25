package com.monolithiot.iot.templates.service;

import com.monolithiot.iot.service.basic.AbstractService;
import com.monolithiot.iot.templates.entity.TemplateGroup;

import java.util.List;

/**
 * Create by 郭文梁 2019/7/25 17:39
 * TemplateGroupService
 * 模板分组相关业务行为定义
 *
 * @author 郭文梁
 * @data 2019/7/25 17:39
 */
public interface TemplateGroupService extends AbstractService<TemplateGroup> {
    /**
     * 创建模板分组
     *
     * @param userId 用户ID
     * @param group  分组对象
     * @return TemplateGroup Entity
     */
    TemplateGroup create(Integer userId, TemplateGroup group);

    /**
     * 添加模板ID
     *
     * @param group       分组
     * @param templateIds 模板ID列表
     * @return TG
     */
    TemplateGroup appendTemplate(TemplateGroup group, List<String> templateIds);
}
