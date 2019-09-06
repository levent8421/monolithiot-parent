package com.monolithiot.iot.notification.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Create by 郭文梁 2019/9/6 13:37
 * MqReturnCallback
 * Message Queue return callback(handler)
 *
 * @author 郭文梁
 * @data 2019/9/6 13:37
 */
@Component
@Slf4j
public class MqReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("Mq Message return , message=[{}], replyCode=[{}], replyText=[{}],exchange=[{}], routingKey=[{}]",
                message, replyCode, replyText, exchange, routingKey);
    }
}
