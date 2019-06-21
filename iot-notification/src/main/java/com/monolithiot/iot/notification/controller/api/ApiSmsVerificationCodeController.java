package com.monolithiot.iot.notification.controller.api;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.utils.ParamChecker;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.notification.entity.SmsVerificationCode;
import com.monolithiot.iot.notification.service.SmsVerificationCodeService;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 郭文梁 2019/6/21 0021 16:03
 * ApiSmsVerificationCodeController
 * 系统内调用的短信验证码控制器
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
@RestController
@RequestMapping("/api/sms-verification-code")
public class ApiSmsVerificationCodeController extends AbstractEntityController<SmsVerificationCode> {
    private final SmsVerificationCodeService smsVerificationCodeService;

    /**
     * 构造时指定业务组件
     *
     * @param service 业务组件
     */
    protected ApiSmsVerificationCodeController(SmsVerificationCodeService service) {
        super(service);
        this.smsVerificationCodeService = service;
    }

    /**
     * 验证短信验证码内容
     *
     * @param param 参数
     * @return 验证结果 GR
     */
    @PostMapping("/verify")
    public GeneralResult<Boolean> verify(@RequestBody SmsVerificationCode param) {
        checkVerifyParam(param);
        boolean res = smsVerificationCodeService.verify(param.getTraceNo(), param.getVerificationCode());
        return GeneralResult.ok(res);
    }

    /**
     * 检查验证参数
     *
     * @param param 参数
     */
    private void checkVerifyParam(SmsVerificationCode param) {
        final Class<BadRequestException> ex = BadRequestException.class;
        ParamChecker.notNull(param, ex, "参数未传");
        ParamChecker.notEmpty(param.getTraceNo(), ex, "记录号必填");
        ParamChecker.notEmpty(param.getVerificationCode(), ex, "短信验证码必填");
    }
}
