package com.monolithiot.iot.notification.service.impl;

import com.monolithiot.iot.notification.entity.Notification;
import com.monolithiot.iot.notification.mapper.NotificationMapper;
import com.monolithiot.iot.notification.service.NotificationService;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends AbstractServiceImpl<Notification> implements NotificationService {
    private final NotificationMapper notificationMapper;

    public NotificationServiceImpl(NotificationMapper mapper) {
        super(mapper);
        this.notificationMapper = mapper;
    }
}
