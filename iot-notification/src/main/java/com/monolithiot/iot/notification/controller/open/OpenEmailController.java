package com.monolithiot.iot.notification.controller.open;

import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.notification.dto.EmailData;
import com.monolithiot.iot.notification.util.EmailUtils;
import com.monolithiot.iot.web.controller.AbstractController;
import lombok.val;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 郭文梁 2019/9/5 18:55
 * OpenEmailController
 * Email Controler
 *
 * @author 郭文梁
 * @data 2019/9/5 18:55
 */
@RestController
@RequestMapping("/open/email")
public class OpenEmailController extends AbstractController {
    private final JavaMailSender javaMailSender;

    public OpenEmailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/send")
    public GeneralResult<?> send(@RequestParam String target,
                                 @RequestParam String msg,
                                 @RequestParam String from,
                                 @RequestParam String subject) {

        val data = new EmailData();
        data.setTarget(target);
        data.setContent(msg);
        data.setSubject(subject);
        EmailUtils.send(javaMailSender, from, data);
        return GeneralResult.ok(data);
    }
}
