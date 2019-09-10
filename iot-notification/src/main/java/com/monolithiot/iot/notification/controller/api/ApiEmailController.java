package com.monolithiot.iot.notification.controller.api;

import com.monolithiot.iot.commons.dto.EmailDto;
import com.monolithiot.iot.notification.entity.Email;
import com.monolithiot.iot.notification.service.EmailService;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 郭文梁 2019/9/10 9:09
 * ApiEmailController
 * Email Api Controller
 *
 * @author 郭文梁
 * @data 2019/9/10 9:09
 */
@RestController
@RequestMapping("/api/email")
public class ApiEmailController extends AbstractEntityController<Email> {
    private final EmailService emailService;

    /**
     * 构造时指定业务组件
     *
     * @param emailService 业务组件
     */
    public ApiEmailController(EmailService emailService) {
        super(emailService);
        this.emailService = emailService;
    }

    /**
     * Find email by traceId
     *
     * @param traceId traceId
     * @return Email
     */
    @GetMapping("/traceId/{traceId}")
    public EmailDto findByTraceId(@PathVariable("traceId") String traceId) {
        val email = emailService.requireByTraceId(traceId);
        val res = new EmailDto();
        res.setTarget(email.getTarget());
        res.setSubject(email.getSubject());
        res.setUserId(email.getUserId());
        res.setIntention(email.getIntention());
        return res;
    }
}
