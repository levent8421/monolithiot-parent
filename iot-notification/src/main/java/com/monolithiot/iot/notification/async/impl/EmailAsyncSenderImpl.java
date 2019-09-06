package com.monolithiot.iot.notification.async.impl;

import com.monolithiot.iot.commons.utils.RandomUtils;
import com.monolithiot.iot.notification.async.EmailAsyncSender;
import com.monolithiot.iot.notification.dto.EmailData;
import com.monolithiot.iot.notification.entity.Email;
import com.monolithiot.iot.notification.mapper.EmailMapper;
import com.monolithiot.iot.notification.template.TemplateCache;
import com.monolithiot.iot.notification.util.EmailUtils;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by 郭文梁 2019/9/6 13:27
 * EmailAsyncSenderImpl
 * Email Asynchronous Sender Implementation
 * implement by rabbitMq Queue
 *
 * @author 郭文梁
 * @data 2019/9/6 13:27
 */
@Component
@Slf4j
public class EmailAsyncSenderImpl extends AbstractServiceImpl<Email> implements EmailAsyncSender {
    private static final String EXCHANGE = "exchange.email";
    private static final String ROUTING_KEY = "email.async.*";
    private final EmailMapper emailMapper;
    private final RabbitTemplate rabbitTemplate;
    private final JavaMailSender javaMailSender;
    private final TemplateCache templateCache;

    public EmailAsyncSenderImpl(EmailMapper mapper,
                                RabbitTemplate rabbitTemplate,
                                JavaMailSender javaMailSender,
                                TemplateCache templateCache) {
        super(mapper);
        this.emailMapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
        this.javaMailSender = javaMailSender;
        this.templateCache = templateCache;
    }

    @Override
    public void send(EmailData emailData) {
        Map<String, Object> properties = new HashMap<>(16);
        properties.put("target", emailData.getTarget());
        properties.put("subject", emailData.getSubject());
        MessageHeaders headers = new MessageHeaders(properties);
        Message<EmailData> message = MessageBuilder.createMessage(emailData, headers);
        CorrelationData randomId = randomId();
        log.info("Push Task: Send email [{}] to [{}]", emailData.getSubject(), emailData.getTarget());
        rabbitTemplate.convertAndSend(EXCHANGE, "email.async.1", message, randomId);
    }

    /**
     * Generate Random Id
     *
     * @return id
     */
    private CorrelationData randomId() {
        val id = RandomUtils.randomPrettyUUIDString();
        return new CorrelationData(id);
    }

    /**
     * Do send email
     *
     * @param message message from queue
     * @param channel chanel
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            value = "email.queue",
                            declare = "false"
                    ),
                    exchange = @Exchange(
                            value = EXCHANGE,
                            declare = "false",
                            type = "topic",
                            ignoreDeclarationExceptions = "true"
                    ),
                    key = ROUTING_KEY
            )
    )
    public void doSend(Message<EmailData> message, Channel channel) throws IOException {
        val deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        EmailData emailData = message.getPayload();
        emailData.setTraceId(RandomUtils.randomPrettyUUIDString());

        log.info("Resolve task: send email [{}] to [{}]", emailData.getSubject(), emailData.getTarget());
        renderEmailContent(emailData);
        try {
            EmailUtils.sendMimeMessage(javaMailSender, emailData);
            saveEmail(emailData);
            ack(deliveryTag, channel, true);
            log.info("Send email success, target=[{}]!", emailData.getTarget());
        } catch (MessagingException e) {
            log.warn("Error on send email [{}] to [{}]", emailData.getSubject(), emailData.getTarget());
            ack(deliveryTag, channel, false);
        }
    }

    /**
     * Render send content
     *
     * @param emailData email data
     */
    private void renderEmailContent(EmailData emailData) {
        val content = templateCache.render(emailData.getTemplateName(), emailData);
        emailData.setContentText(content);
    }

    /**
     * Save Email to database
     *
     * @param emailData email data
     */
    private void saveEmail(EmailData emailData) {
        val email = new Email();
        email.setUserId(emailData.getUserId());
        email.setTraceId(emailData.getTraceId());
        email.setTarget(emailData.getTarget());
        email.setSubject(emailData.getSubject());
        email.setContentText(emailData.getContentText());
        email.setIntention(emailData.getIntention());
        val saveRes = save(email);
        log.info("Save Email [{}],[{}]", saveRes.getTraceId(), saveRes.getTarget());
    }

    /**
     * Message Ack
     *
     * @param deliveryTag deliveryTag
     * @param channel     chanel
     * @param success     success flag
     * @throws IOException IOE
     */
    private void ack(Long deliveryTag, Channel channel, boolean success) throws IOException {
        if (deliveryTag != null) {
            if (success) {
                channel.basicAck(deliveryTag, false);
            } else {
                channel.basicNack(deliveryTag, false, false);
            }
        }
    }
}
