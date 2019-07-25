package com.monolithiot.iot.templates.service.impl;

import com.alibaba.fastjson.JSON;
import com.monolithiot.iot.commons.utils.CollectionUtils;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.monolithiot.iot.templates.entity.TemplateGroup;
import com.monolithiot.iot.templates.repository.sql.TemplateGroupMapper;
import com.monolithiot.iot.templates.service.TemplateGroupService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by 郭文梁 2019/7/25 17:40
 * TemplateGroupServiceImpl
 * 模板分组相关业务行为实现
 *
 * @author 郭文梁
 * @data 2019/7/25 17:40
 */
@Service
public class TemplateGroupServiceImpl extends AbstractServiceImpl<TemplateGroup> implements TemplateGroupService {
    private final TemplateGroupMapper templateGroupMapper;

    public TemplateGroupServiceImpl(TemplateGroupMapper templateGroupMapper) {
        super(templateGroupMapper);
        this.templateGroupMapper = templateGroupMapper;
    }

    @Override
    public TemplateGroup create(Integer userId, TemplateGroup group) {
        group.setUserId(userId);
        val templateIdsJson = JSON.toJSONString(CollectionUtils.deDuplication(group.getTemplates()));
        group.setTemplatesJson(templateIdsJson);
        return save(group);
    }

    @Override
    public TemplateGroup appendTemplate(TemplateGroup group, List<String> templateIds) {
        val originalIds = JSON.parseArray(group.getTemplatesJson(), String.class);
        originalIds.addAll(templateIds);
        group.setTemplatesJson(JSON.toJSONString(CollectionUtils.deDuplication(originalIds)));
        return updateById(group);
    }
}
