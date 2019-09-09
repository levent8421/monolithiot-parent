package com.monolithiot.iot.notification.controller.safe;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.notification.entity.Email;
import com.monolithiot.iot.notification.service.EmailService;
import com.monolithiot.iot.notification.vo.SendEmailParam;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.monolithiot.iot.commons.utils.ParamChecker.notEmpty;
import static com.monolithiot.iot.commons.utils.ParamChecker.notNull;

/**
 * Create by 郭文梁 2019/9/9 18:24
 * EmailController
 * 邮箱相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/9/9 18:24
 */
@RestController
@RequestMapping("/email")
public class EmailController extends AbstractEntityController<Email> {
    private final EmailService emailService;

    /**
     * 构造时指定业务组件
     *
     * @param emailService 业务组件
     */
    public EmailController(EmailService emailService) {
        super(emailService);
        this.emailService = emailService;
    }

    /**
     * 发送更新邮箱地址的邮件
     *
     * @param param   参数
     * @param request request
     * @return GR
     */
    @PostMapping("/send-update-email")
    public GeneralResult<Void> sendUpdateEmail(@RequestBody SendEmailParam param, HttpServletRequest request) {
        notNull(param, BadRequestException.class, "No available params!");
        notEmpty(param.getTarget(), BadRequestException.class, "Send target is required!");
        val userId = requireCurrentUserId(request);
        emailService.sendUpdateEmail(userId, param.getTarget());
        return GeneralResult.ok();
    }
}
