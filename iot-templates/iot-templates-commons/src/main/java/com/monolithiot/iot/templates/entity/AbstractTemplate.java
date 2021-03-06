package com.monolithiot.iot.templates.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.monolithiot.iot.commons.context.ApplicationConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Score;

import javax.persistence.GeneratedValue;
import java.util.Date;
import java.util.List;

/**
 * Create by 郭文梁 2019/8/20 16:24
 * AbstractTemplate
 * 数据模板抽象父类
 *
 * @author 郭文梁
 * @data 2019/8/20 16:24
 */
@Data
public abstract class AbstractTemplate {
    /**
     * ID
     */
    @Id
    @GeneratedValue
    @Field
    private String id;
    /**
     * 匹配分数
     */
    @Score
    private Float score;
    /**
     * 标题
     */
    @Field
    private String title;
    /**
     * 描述
     */
    @Field
    private String description;
    /**
     * Data Type
     */
    @Field
    private String dataType;
    /**
     * 图片列表
     */
    @Field
    private List<String> images;
    /**
     * 是否匿名
     */
    @Field
    private Boolean anonymous;
    /**
     * 作者ID
     */
    @Field
    private Integer authorId;
    /**
     * 作者姓名
     */
    @Field
    private String authorName;
    /**
     * 所属行业
     */
    @Field
    private String industry;
    /**
     * 行业ID
     */
    @Field
    private Integer industryId;
    /**
     * 字段信息
     */
    @Field
    private List<TemplateField> fields;
    /**
     * 创建时间
     */
    @Field
    @JsonFormat(pattern = ApplicationConstants.DateTime.DATE_TIME_FORMATER)
    private Date createTime;
    /**
     * 最后一次修改时间
     */
    @Field
    @JsonFormat(pattern = ApplicationConstants.DateTime.DATE_TIME_FORMATER)
    private Date modifyTime;
}
