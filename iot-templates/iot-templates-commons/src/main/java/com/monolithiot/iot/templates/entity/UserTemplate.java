package com.monolithiot.iot.templates.entity;

import com.monolithiot.iot.commons.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create By leven ont 2019/7/18 19:04
 * Class Name :[UserTemplate]
 * <p>
 * 用户和模板关系实体类
 *
 * @author leven
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_user_template")
public class UserTemplate extends AbstractEntity<Integer> {
    /**
     * 用户ID
     */
    @Column(name = "user_id", length = 10, nullable = false)
    private Integer userId;
    /**
     * 模板ID
     */
    @Column(name = "template_id", length = 20, nullable = false)
    private Long templateId;
    /**
     * 模板对象
     */
    private MeasureTemplate template;
}
