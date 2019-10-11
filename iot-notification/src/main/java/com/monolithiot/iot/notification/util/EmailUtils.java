package com.monolithiot.iot.notification.util;

import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import com.monolithiot.iot.notification.dto.EmailData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Create by 郭文梁 2019/9/5 18:37
 * EmailUtils
 * Email Utils
 *
 * @author 郭文梁
 * @data 2019/9/5 18:37
 */
@Slf4j
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
        message.setText(emailData.getContentText());
        sender.send(message);
    }

    /**
     * Send Mime Email Message
     *
     * @param sender    javaMailSender
     * @param emailData emailData
     * @throws MessagingException exception
     */
    public static void sendMimeMessage(JavaMailSender sender, EmailData emailData) throws MessagingException {
        log.info("Send [{}] to [{}]", emailData.getSubject(), emailData.getTarget());
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(emailData.getFrom());
        messageHelper.setTo(emailData.getTarget());
        messageHelper.setSubject(emailData.getSubject());
        messageHelper.setText(emailData.getContentText(), true);
        sender.send(mimeMessage);
        log.info("Send Email Success!");
    }

    /**
     * Build Email From Address Object
     *
     * @param emailFromName nickname
     * @param emailFrom     from email
     * @return address
     */
    public static InternetAddress buildFromAddress(String emailFromName, String emailFrom) {
        try {
            return new InternetAddress(emailFromName + "<" + emailFrom + ">");
        } catch (AddressException e) {
            throw new InternalServerErrorException("Error on build email from address!", e);
        }
    }
}
