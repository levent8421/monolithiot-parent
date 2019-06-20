package com.monolithiot.iot.notification.mapper;

import com.monolithiot.iot.notification.entity.Notification;
import com.monolithiot.iot.repository.AbstractMapper;
import org.springframework.stereotype.Repository;

/**
 * Create by 郭文梁 2019/6/20 0020 15:56
 * NotificationMapper
 * 通知记录相关数据库访问组件
 *
 * @author 郭文梁
 * @data 2019/6/20 0020
 */
@Repository
public interface NotificationMapper extends AbstractMapper<Notification> {

}
