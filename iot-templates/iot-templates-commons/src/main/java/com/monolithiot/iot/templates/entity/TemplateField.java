package com.monolithiot.iot.templates.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Create by 郭文梁 2019/7/12 0012 10:20
 * TemplateField
 * 模板字段
 *
 * @author 郭文梁
 * @data 2019/7/12 0012
 */
@Data
@NoArgsConstructor
public class TemplateField implements Serializable {
    /**
     * 字段名称 用作程序标识
     */
    private String name;
    /**
     * 显示名称
     */
    private String displayText;
    /**
     * 单位名称
     */
    private String unitText;
    /**
     * 单位权重
     */
    private Double unitWeight;
    /**
     * 表达式
     */
    private String expression;
    /**
     * 值
     */
    private Integer value;
    /**
     * 描述
     */
    private String description;

    public TemplateField(String name, String displayText) {
        this.name = name;
        this.displayText = displayText;
    }
}
