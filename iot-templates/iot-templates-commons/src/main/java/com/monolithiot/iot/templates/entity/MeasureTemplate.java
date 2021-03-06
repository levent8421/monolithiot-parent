package com.monolithiot.iot.templates.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Create by 郭文梁 2019/7/16 17:09
 * MeasureTemplate
 * 测量模板
 *
 * @author 郭文梁
 * @data 2019/7/16 17:09
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Document(indexName = "repository_1", type = "templates")
public class MeasureTemplate extends AbstractTemplate {

}
