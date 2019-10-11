package com.monolithiot.iot.notification.service.impl;

import com.monolithiot.iot.commons.exception.ResourceNotFoundException;
import com.monolithiot.iot.notification.async.EmailAsyncSender;
import com.monolithiot.iot.notification.dto.EmailData;
import com.monolithiot.iot.notification.entity.Email;
import com.monolithiot.iot.notification.mapper.EmailMapper;
import com.monolithiot.iot.notification.service.EmailService;
import com.monolithiot.iot.notification.util.EmailUtils;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.internet.InternetAddress;

/**
 * Create by 郭文梁 2019/9/6 9:21
 * EmailServiceImpl
 * Email Service Implementation
 *
 * @author 郭文梁
 * @data 2019/9/6 9:21
 */
@Service
public class EmailServiceImpl extends AbstractServiceImpl<Email> implements EmailService {
    private static final String REGISTER_EMAIL_TEMPLATE_NAME = "register-email.html";
    private static final String REGISTER_EMAIL_SUBJECT = "磐石电气注册邮件";
    private static final String UPDATE_EMAIL_SUBJECT = "磐石电气_更新邮箱";
    private static final String FORGET_PASSWORD_EMAIL_SUBJECT = "磐石电气_重置密码";
    private static final String UPDATE_EMAIL_TEMPLATE_NAME = "update-email.html";
    private static final String FORGET_PASSWORD_EMAIL_TEMPLATE_NAME = "forget-password-email.html";
    private static final String EMAIL_FROM_NAME = "磐石电气";
    @Value("${spring.mail.username}")
    private String emailFrom;
    private InternetAddress emailFromAddress;
    private final EmailMapper emailMapper;
    private final EmailAsyncSender emailAsyncSender;

    /**
     * Init this component
     */
    @PostConstruct
    public void init() {
        //init emailFromAddress filed
        emailFromAddress = EmailUtils.buildFromAddress(EMAIL_FROM_NAME, emailFrom);
    }

    public EmailServiceImpl(EmailMapper mapper,
                            EmailAsyncSender emailAsyncSender) {
        super(mapper);
        this.emailMapper = mapper;
        this.emailAsyncSender = emailAsyncSender;
    }

    @Override
    public Email sendRegisterEmail(String recipient) {
        val emailData = new EmailData();
        emailData.setSubject(REGISTER_EMAIL_SUBJECT);
        emailData.setTarget(recipient);
        emailData.setFrom(emailFromAddress);
        emailData.setTemplateName(REGISTER_EMAIL_TEMPLATE_NAME);
        emailData.setIntention(Email.INTENTION_REGISTER);
        emailAsyncSender.send(emailData);
        return null;
    }

    @Override
    public void sendUpdateEmail(Integer userId, String recipient) {
        val emailData = new EmailData();
        emailData.setSubject(UPDATE_EMAIL_SUBJECT);
        emailData.setTarget(recipient);
        emailData.setTemplateName(UPDATE_EMAIL_TEMPLATE_NAME);
        emailData.setFrom(emailFromAddress);
        emailData.setIntention(Email.INTENTION_UPDATE_EMAIL);
        emailData.setUserId(userId);
        emailAsyncSender.send(emailData);
    }

    @Override
    public Email requireByTraceId(String traceId) {
        val query = new Email();
        query.setTraceId(traceId);
        val res = findOneByQuery(query);
        if (res == null) {
            throw new ResourceNotFoundException("The Email for traceId [" + traceId + "] could not be found!");
        }
        return res;
    }

    @Override
    public void sendForgetPasswordEmail(String email) {
        val emailData = new EmailData();
        emailData.setSubject(FORGET_PASSWORD_EMAIL_SUBJECT);
        emailData.setTarget(email);
        emailData.setTemplateName(FORGET_PASSWORD_EMAIL_TEMPLATE_NAME);
        emailData.setFrom(emailFromAddress);
        emailData.setIntention(Email.INTENTION_RESET_PASSWORD);
        emailAsyncSender.send(emailData);
    }
}
