package com.monolithiot.iot.notification.service.impl;

import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import com.monolithiot.iot.notification.async.EmailAsyncSender;
import com.monolithiot.iot.notification.dto.EmailData;
import com.monolithiot.iot.notification.entity.Email;
import com.monolithiot.iot.notification.mapper.EmailMapper;
import com.monolithiot.iot.notification.service.EmailService;
import com.monolithiot.iot.notification.util.EmailUtils;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.PostConstruct;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.HashMap;

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
    private static final String EMAIL_FROM_NAME = "磐石电气";
    @Value("${spring.mail.username}")
    private String emailFrom;
    private InternetAddress emailFromAddress;
    private final EmailMapper emailMapper;
    private final Configuration templateConfiguration;
    private final TemplateCache templateCache = new TemplateCache();
    private final JavaMailSender javaMailSender;
    private final EmailAsyncSender emailAsyncSender;

    /**
     * Init this component
     */
    @PostConstruct
    public void init() {
        //init emailFromAddress filed
        emailFromAddress = EmailUtils.buildFromAddress(EMAIL_FROM_NAME, emailFrom);
    }

    /**
     * Create by 郭文梁 2019/9/6 9:43
     * EmailServiceImpl
     * TemplateCache
     * FreeMarker Template Cache
     *
     * @author 郭文梁
     * @data 2019/9/6 9:43
     */
    private class TemplateCache extends HashMap<String, Template> {
        TemplateCache() {
            super(16);
        }

        /**
         * Load Template
         *
         * @param templateConfiguration freemarker template configuration
         * @param name                  template name
         * @return Template Object
         */
        Template loadTemplate(Configuration templateConfiguration, String name) {
            var template = get(name);
            if (template == null) {
                synchronized (this) {
                    try {
                        template = templateConfiguration.getTemplate(name);
                        put(name, template);
                    } catch (IOException e) {
                        throw new InternalServerErrorException("Error on load email template [" + name + "]!", e);
                    }
                }
            }
            return template;
        }
    }

    public EmailServiceImpl(EmailMapper mapper,
                            @Qualifier("templateConfiguration") Configuration templateConfiguration,
                            JavaMailSender javaMailSender, EmailAsyncSender emailAsyncSender) {
        super(mapper);
        this.emailMapper = mapper;
        this.templateConfiguration = templateConfiguration;
        this.javaMailSender = javaMailSender;
        this.emailAsyncSender = emailAsyncSender;
    }

    @Override
    public Email sendRegisterEmail(String recipient) {
        val modelData = new HashMap<String, Object>(16);
        val template = templateCache.loadTemplate(templateConfiguration, REGISTER_EMAIL_TEMPLATE_NAME);
        final String renderResult;
        try {
            renderResult = FreeMarkerTemplateUtils.processTemplateIntoString(template, modelData);
        } catch (TemplateException | IOException e) {
            throw new InternalServerErrorException("Error on render email template [" + REGISTER_EMAIL_TEMPLATE_NAME + "]!", e);
        }
        val emailData = new EmailData();
        emailData.setSubject(REGISTER_EMAIL_SUBJECT);
        emailData.setContent(renderResult);
        emailData.setTarget(recipient);
        emailData.setFrom(emailFromAddress);
        emailAsyncSender.send(emailData);
        return null;
    }

}
