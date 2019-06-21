package com.monolithiot.iot.notification.controller.open;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.utils.ParamChecker;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.notification.entity.SmsVerificationCode;
import com.monolithiot.iot.notification.service.SmsVerificationCodeService;
import com.monolithiot.iot.notification.vo.SmsPreSendVo;
import com.monolithiot.iot.notification.vo.SmsSendParam;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Create by 郭文梁 2019/6/21 0021 13:30
 * SmsVerificationCodeController
 * 短信验证码开放数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
@RestController
@RequestMapping("/open/sms-verification-code")
@Slf4j
public class SmsVerificationCodeController extends AbstractEntityController<SmsVerificationCode> {
    private final SmsVerificationCodeService smsVerificationCodeService;

    /**
     * 构造时指定业务组件
     *
     * @param service 业务组件
     */
    protected SmsVerificationCodeController(SmsVerificationCodeService service) {
        super(service);
        this.smsVerificationCodeService = service;
    }

    /**
     * 短信验证码预发送
     *
     * @param param 发送参数
     * @return GR
     */
    @PutMapping("/pre-send")
    public GeneralResult<SmsPreSendVo> preSend(@RequestBody SmsSendParam param) {
        ParamChecker.notNull(param, BadRequestException.class, "参数未传");
        ParamChecker.notEmpty(param.getTarget(), BadRequestException.class, "发送目标必填");
        SmsPreSendVo res = smsVerificationCodeService.preSend(param.getTarget());
        return GeneralResult.ok(res);
    }

    /**
     * 发送短信验证码
     *
     * @param param 参数
     * @return GR
     */
    @PostMapping("/send")
    public GeneralResult<SmsVerificationCode> send(@RequestBody SmsVerificationCode param) {
        checkSendParam(param);
        SmsVerificationCode res = smsVerificationCodeService.send(param.getTraceNo(), param.getPreVerificationCode());
        return GeneralResult.ok(res);
    }

    /**
     * 检查验证码发送参数
     *
     * @param param 参数
     */
    private void checkSendParam(SmsVerificationCode param) {
        final Class<BadRequestException> ex = BadRequestException.class;
        ParamChecker.notNull(param, ex, "参数未传");
        ParamChecker.notEmpty(param.getTraceNo(), ex, "记录号必填");
        ParamChecker.notEmpty(param.getPreVerificationCode(), ex, "预发送验证码内容必填");
    }
}
