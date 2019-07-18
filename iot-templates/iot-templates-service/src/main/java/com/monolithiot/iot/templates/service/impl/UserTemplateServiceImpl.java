package com.monolithiot.iot.templates.service.impl;

import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.monolithiot.iot.templates.repository.sql.UserTemplateMapper;
import com.monolithiot.iot.templates.entity.MeasureTemplate;
import com.monolithiot.iot.templates.entity.UserTemplate;
import com.monolithiot.iot.templates.service.TemplateService;
import com.monolithiot.iot.templates.service.UserTemplateService;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * Create By leven ont 2019/7/18 21:49
 * Class Name :[UserTemplateServiceImpl]
 * <p>
 * 用户模板相关业务行为实现
 *
 * @author leven
 */
@Service
public class UserTemplateServiceImpl extends AbstractServiceImpl<UserTemplate> implements UserTemplateService {
    private final UserTemplateMapper userTemplateMapper;
    private final TemplateService templateService;

    public UserTemplateServiceImpl(UserTemplateMapper mapper,
                                   TemplateService templateService) {
        super(mapper);
        this.userTemplateMapper = mapper;
        this.templateService = templateService;
    }

    @Override
    public UserTemplate create(Integer userId, MeasureTemplate template) {
        val res = new UserTemplate();
        res.setUserId(userId);
        val templateSaveRes = templateService.save(template);
        res.setTemplateId(templateSaveRes.getId());
        return save(res);
    }
}
