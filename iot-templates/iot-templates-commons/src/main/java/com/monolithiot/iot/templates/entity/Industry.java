package com.monolithiot.iot.templates.entity;

import com.monolithiot.iot.commons.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create By leven ont 2019/8/26 20:58
 * Class Name :[Industry]
 * <p>
 * 行业类型实体类
 *
 * @author leven
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_industry")
public class Industry extends AbstractEntity<Integer> {
    /**
     * 行业名称
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 排序号 反向排序
     */
    @Column(name = "order_num", length = 10, nullable = false)
    private Integer orderNum;
}
