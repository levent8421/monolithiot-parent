package com.monolithiot.iot.notification.util;

import com.monolithiot.iot.notification.dto.EmailData;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Create by 郭文梁 2019/9/5 18:37
 * EmailUtils
 * Email Utils
 *
 * @author 郭文梁
 * @data 2019/9/5 18:37
 */
public class EmailUtils {
    /**
     * Suppresses default constructor, ensuring non-instantiability.
     */
    private EmailUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Send Email
     *
     * @param sender    javaMailSender
     * @param from      email from
     * @param emailData email data
     */
    public static void send(JavaMailSender sender, String from, EmailData emailData) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(emailData.getTarget());
        message.setSubject(emailData.getSubject());
        message.setText(emailData.getContent());
        sender.send(message);
    }
}
