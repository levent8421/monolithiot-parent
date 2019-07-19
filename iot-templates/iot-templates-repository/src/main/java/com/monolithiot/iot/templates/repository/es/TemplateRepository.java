package com.monolithiot.iot.templates.repository.es;

import com.monolithiot.iot.templates.entity.MeasureTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Create by 郭文梁 2019/7/19 15:46
 * TemplateRepository
 * 模板相关数据存储组件 定义
 *
 * @author 郭文梁
 * @data 2019/7/19 15:46
 */
public interface TemplateRepository extends ElasticsearchRepository<MeasureTemplate, String> {
}
