package com.monolithiot.iot.templates.repository.sql;

import com.monolithiot.iot.repository.AbstractMapper;
import com.monolithiot.iot.templates.entity.Industry;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create By leven ont 2019/8/26 21:13
 * Class Name :[IndustryMapper]
 * <p>
 * 行业相关数据库访问组件
 *
 * @author leven
 */
@Repository
public interface IndustryMapper extends AbstractMapper<Industry> {
    /**
     * 查询所有行业信息 （排序）
     * @return Industry List
     */
    List<Industry> selectAllWithOrder();
}
