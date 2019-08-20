package com.monolithiot.iot.templates.repository.es;

import com.monolithiot.iot.templates.entity.MeasureData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by 郭文梁 2019/8/20 16:00
 * MeasureDataRepository
 * 测量数据相关数据库访问组件（ES）
 *
 * @author 郭文梁
 * @data 2019/8/20 16:00
 */
@Repository
public interface MeasureDataRepository extends ElasticsearchRepository<MeasureData, String> {
}
