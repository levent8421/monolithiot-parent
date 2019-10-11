package com.monolithiot.iot.notification.controller.open;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.notification.feign.UserFeignClient;
import com.monolithiot.iot.notification.service.EmailService;
import com.monolithiot.iot.notification.vo.SendEmailParam;
import com.monolithiot.iot.web.controller.AbstractController;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import static com.monolithiot.iot.commons.utils.ParamChecker.notEmpty;
import static com.monolithiot.iot.commons.utils.ParamChecker.notNull;

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
    private final UserFeignClient userFeignClient;

    public OpenEmailController(EmailService emailService,
                               UserFeignClient userFeignClient) {
        this.emailService = emailService;
        this.userFeignClient = userFeignClient;
    }

    @GetMapping("/send")
    public GeneralResult<?> send(@RequestParam String target) {

        val sendRes = emailService.sendRegisterEmail(target);
        return GeneralResult.ok(sendRes);
    }

    /**
     * 发送忘记密码（重置密码）的邮件
     *
     * @param param 参数
     * @return GR
     */
    @PostMapping("/send-forget-password-email")
    public GeneralResult<Void> setForgetPasswordEmail(@RequestBody SendEmailParam param) {
        checkSendEmailParam(param);
        val email = param.getTarget();
        if (!userFeignClient.existsByEmail(email)) {
            throw new BadRequestException("用户不存在！");
        }
        emailService.sendForgetPasswordEmail(email);
        return GeneralResult.ok();
    }

    /**
     * 检查发送邮件的参数
     *
     * @param param param
     */
    private void checkSendEmailParam(SendEmailParam param) {
        val ex = BadRequestException.class;
        notNull(param, ex, "No Available Params!");
        notEmpty(param.getTarget(), ex, "Send Target is required!");
    }
}
