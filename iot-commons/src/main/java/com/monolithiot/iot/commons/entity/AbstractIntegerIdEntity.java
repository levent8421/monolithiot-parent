package com.monolithiot.iot.commons.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Create by 郭文梁 2019/6/15 0015 11:19
 * AbstractIntegerIdEntity
 * ID类型为Integer的实体抽象类
 *
 * @author 郭文梁
 * @data 2019/6/15 0015
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractIntegerIdEntity extends AbstractEntity<Integer> {
    
}
