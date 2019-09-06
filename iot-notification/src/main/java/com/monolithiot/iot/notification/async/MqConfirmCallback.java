package com.monolithiot.iot.notification.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Create by 郭文梁 2019/9/6 13:37
 * MqConfirmCallback
 * RabbitMq Message Confirm Callback(handler)
 *
 * @author 郭文梁
 * @data 2019/9/6 13:37
 */
@Component
@Slf4j
public class MqConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("Mq Message Confirm ,correlationData=[{}],ack=[{}],cause=[{}]", correlationData, ack, cause);
    }
}
