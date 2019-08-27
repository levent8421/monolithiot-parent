package com.monolithiot.iot.templates.entity;

import com.monolithiot.iot.commons.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.catalina.User;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.List;

/**
 * Create by 郭文梁 2019/7/25 17:15
 * TemplateGroup
 * 模板分组
 *
 * @author 郭文梁
 * @data 2019/7/25 17:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_template_group")
public class TemplateGroup extends AbstractEntity<Integer> {
    /**
     * 用户ID
     */
    @Column(name = "user_id", length = 10, nullable = false)
    private Integer userId;
    /**
     * 关联的用户对象
     */
    private User user;
    /**
     * 分组名称
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 标题
     */
    @Column(name = "title")
    private String title;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 数据类型
     */
    @Column(name = "data_type")
    private String dataType;
    /**
     * 模板ID列表 JSON方式存储
     */
    @Column(name = "templates_json")
    private String templatesJson;
    /**
     * 通过templatesJson 字段解析出的模板ID列表
     */
    private List<String> templates;
    /**
     * 行业ID
     */
    @Column(name = "industry_id", length = 10)
    private Integer industryId;
    /**
     * 行业对象 for industryId
     */
    private Industry industry;
}
