package com.monolithiot.iot.templates.service.impl;

import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.monolithiot.iot.templates.entity.Industry;
import com.monolithiot.iot.templates.repository.sql.IndustryMapper;
import com.monolithiot.iot.templates.service.IndustryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By leven ont 2019/8/26 21:46
 * Class Name :[IndustryServiceImpl]
 * <p>
 * 行业相关业务行为实现
 *
 * @author leven
 */
@Service
public class IndustryServiceImpl extends AbstractServiceImpl<Industry> implements IndustryService {
    private final IndustryMapper industryMapper;

    public IndustryServiceImpl(IndustryMapper mapper) {
        super(mapper);
        this.industryMapper = mapper;
    }

    @Override
    public List<Industry> findAllWithOrder() {
        return industryMapper.selectAllWithOrder();
    }
}
