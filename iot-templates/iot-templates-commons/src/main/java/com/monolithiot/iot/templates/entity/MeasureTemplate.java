package com.monolithiot.iot.templates.entity;

import com.monolithiot.iot.commons.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Create by 郭文梁 2019/7/16 17:09
 * MeasureTemplate
 * 测量模板
 *
 * @author 郭文梁
 * @data 2019/7/16 17:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MeasureTemplate extends AbstractEntity<Long> {
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 图片列表
     */
    private List<String> images;
    /**
     * 作者ID
     */
    private Integer authorId;
    /**
     * 作者姓名
     */
    private String authorName;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 字段信息
     */
    private List<TemplateField> fields;
}
