package com.monolithiot.iot.templates.entity;

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
@Document(indexName = "measure_data_index_1", type = "measure_data")
public class MeasureData extends AbstractTemplate {
}
