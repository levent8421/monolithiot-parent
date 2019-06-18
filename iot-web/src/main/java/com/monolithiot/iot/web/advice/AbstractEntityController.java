package com.monolithiot.iot.web.advice;

import com.monolithiot.iot.commons.entity.AbstractEntity;
import com.monolithiot.iot.service.basic.AbstractService;
import com.monolithiot.iot.web.controller.AbstractController;

/**
 * Create by 郭文梁 2019/5/18 0018 12:39
 * AbstractEntityController
 * 数据对象控制器
 *
 * @author 郭文梁
 * @data 2019/5/18 0018
 */
public abstract class AbstractEntityController<Entity extends AbstractEntity> extends AbstractController {
    /**
     * 业务组件
     */
    private final AbstractService<Entity> service;

    /**
     * 构造时指定业务组件
     *
     * @param service 业务组件
     */
    protected AbstractEntityController(AbstractService<Entity> service) {
        if (service == null) {
            throw new NullPointerException("The service for entity could not be null!");
        }
        this.service = service;
    }
}
