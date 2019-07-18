package com.monolithiot.iot.templates.service;

import com.monolithiot.iot.service.basic.AbstractService;
import com.monolithiot.iot.templates.entity.MeasureTemplate;
import com.monolithiot.iot.templates.entity.UserTemplate;

/**
 * Create By leven ont 2019/7/18 21:46
 * Class Name :[UserTemplateService]
 * <p>
 * 用户模板相关业务组件
 *
 * @author leven
 */
public interface UserTemplateService extends AbstractService<UserTemplate> {
    /**
     * 创建用户模板
     *
     * @param userId   用户ID
     * @param template 模板信息
     * @return UserTemplate
     */
    UserTemplate create(Integer userId, MeasureTemplate template);
}
