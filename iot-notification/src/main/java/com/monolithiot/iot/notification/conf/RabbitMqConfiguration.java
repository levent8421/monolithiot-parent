package com.monolithiot.iot.notification.conf;

import com.monolithiot.iot.notification.async.MqConfirmCallback;
import com.monolithiot.iot.notification.async.MqReturnCallback;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Create by 郭文梁 2019/9/6 13:43
 * RabbitMqConfiguration
 * RabbitMQ auto configuration
 *
 * @author 郭文梁
 * @data 2019/9/6 13:43
 */
@Configuration
public class RabbitMqConfiguration {
    private final RabbitTemplate rabbitTemplate;
    private final MqReturnCallback mqReturnCallback;
    private final MqConfirmCallback mqConfirmCallback;
    private final ConnectionFactory connectionFactory;

    public RabbitMqConfiguration(RabbitTemplate rabbitTemplate,
                                 MqReturnCallback mqReturnCallback,
                                 MqConfirmCallback mqConfirmCallback,
                                 ConnectionFactory connectionFactory) {
        this.rabbitTemplate = rabbitTemplate;
        this.mqReturnCallback = mqReturnCallback;
        this.mqConfirmCallback = mqConfirmCallback;
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setReturnCallback(mqReturnCallback);
        rabbitTemplate.setConfirmCallback(mqConfirmCallback);
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory);
    }
}
