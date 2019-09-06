package com.monolithiot.iot.notification.controller.open;

import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.notification.service.EmailService;
import com.monolithiot.iot.web.controller.AbstractController;
import lombok.val;
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
    private final EmailService emailService;

    public OpenEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send")
    public GeneralResult<?> send(@RequestParam String target) {

        val sendRes = emailService.sendRegisterEmail(target);
        return GeneralResult.ok(sendRes);
    }
}
