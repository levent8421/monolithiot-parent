package com.monolithiot.iot.notification.async;

import com.monolithiot.iot.notification.dto.EmailData;
import com.monolithiot.iot.notification.entity.Email;
import com.monolithiot.iot.service.basic.AbstractService;

/**
 * Create by 郭文梁 2019/9/6 13:25
 * EmailAsyncSender
 * Email Asynchronous Sender
 *
 * @author 郭文梁
 * @data 2019/9/6 13:25
 */
public interface EmailAsyncSender extends AbstractService<Email> {
    /**
     * 异步发送email
     *
     * @param emailData data
     */
    void send(EmailData emailData);
}
