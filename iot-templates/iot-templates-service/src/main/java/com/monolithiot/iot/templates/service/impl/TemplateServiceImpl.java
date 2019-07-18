package com.monolithiot.iot.templates.service.impl;

import com.monolithiot.iot.templates.entity.MeasureTemplate;
import com.monolithiot.iot.templates.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

/**
 * Create By leven ont 2019/7/18 22:53
 * Class Name :[TemplateServiceImpl]
 * <p>
 * 模板相关业务行为实现
 *
 * @author leven
 */
@Slf4j
@Component
public class TemplateServiceImpl implements TemplateService {
    @Override
    public MeasureTemplate save(MeasureTemplate measureTemplate) {
        val id = 1L;
        log.debug("Set id for template [{}]", id);
        measureTemplate.setId(id);
        return measureTemplate;
    }
}
