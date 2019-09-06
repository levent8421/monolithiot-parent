package com.monolithiot.iot.notification.service;

import com.monolithiot.iot.notification.entity.Email;
import com.monolithiot.iot.service.basic.AbstractService;

/**
 * Create by 郭文梁 2019/9/6 9:21
 * EmailService
 * Email Service Interface
 *
 * @author 郭文梁
 * @data 2019/9/6 9:21
 */
public interface EmailService extends AbstractService<Email> {
    /**
     * 发送注册邮件
     * send email for register
     *
     * @param recipient recipient
     * @return Send Result
     */
    Email sendRegisterEmail(String recipient);
}
