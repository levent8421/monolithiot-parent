package com.monolithiot.iot.notification.controller.api;

import com.monolithiot.iot.notification.entity.Notification;
import com.monolithiot.iot.notification.service.NotificationService;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 郭文梁 2019/6/20 0020 16:08
 * ApiNotificationController
 * 系统内部通知相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/6/20 0020
 */
@RestController
@RequestMapping("/api/notification")
public class ApiNotificationController extends AbstractEntityController<Notification> {
    private final NotificationService notificationService;

    /**
     * 构造时指定业务组件
     *
     * @param service 业务组件
     */
    protected ApiNotificationController(NotificationService service) {
        super(service);
        this.notificationService = service;
    }

}
