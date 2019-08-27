package com.monolithiot.iot.templates.entity;

import com.monolithiot.iot.templates.context.TemplatesConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Create by 郭文梁 2019/8/20 15:39
 * MeasureDate
 * 测量数据实体类
 *
 * @author 郭文梁
 * @data 2019/8/20 15:39
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Document(indexName = TemplatesConstants.ElasticSearch.MEASURE_DATA_INDEX,
        type = TemplatesConstants.ElasticSearch.MEASURE_DATA_TYPE)
public class MeasureData extends AbstractTemplate {
}
