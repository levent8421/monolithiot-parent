package com.monolithiot.iot.notification.service.impl;

import com.google.code.kaptcha.Producer;
import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import com.monolithiot.iot.commons.exception.ResourceNotFoundException;
import com.monolithiot.iot.commons.utils.CommonIOUtils;
import com.monolithiot.iot.commons.utils.RandomUtils;
import com.monolithiot.iot.commons.utils.TextUtils;
import com.monolithiot.iot.notification.api.YunPian;
import com.monolithiot.iot.notification.entity.SmsVerificationCode;
import com.monolithiot.iot.notification.mapper.SmsVerificationCodeMapper;
import com.monolithiot.iot.notification.service.SmsVerificationCodeService;
import com.monolithiot.iot.notification.vo.SmsPreSendVo;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.yunpian.sdk.model.SmsSingleSend;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Create by 郭文梁 2019/6/21 0021 11:34
 * SmsVerificationCodeServiceImpl
 * 短信验证码相关业务行为实现
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
@Service
@Slf4j
public class SmsVerificationCodeServiceImpl extends AbstractServiceImpl<SmsVerificationCode> implements SmsVerificationCodeService {
    /**
     * 验证码长度
     */
    private static final int SMS_VERIFICATION_CODE_LENGTH = 6;
    /**
     * 短信验证码模板
     */
    private static final String VERIFICATION_CODE_SMS_TEMPLATE = "【磐石电气】您的验证码是%s。如非本人操作，请忽略本短信";
    /**
     * 验证码图片格式
     */
    private static final String VERIDICATION_CODE_IMAGE_FORMAT = "png";
    /**
     * 默认验证码有效时间:10分钟
     */
    private static final int DEFAULT_EXPIRE_IN = 10 * 60;
    private final SmsVerificationCodeMapper smsVerificationCodeMapper;
    private final Producer kaptchaProducer;
    private final YunPian yunPian;

    public SmsVerificationCodeServiceImpl(SmsVerificationCodeMapper mapper,
                                          Producer kaptchaProducer,
                                          YunPian yunPian) {
        super(mapper);
        this.smsVerificationCodeMapper = mapper;
        this.kaptchaProducer = kaptchaProducer;
        this.yunPian = yunPian;
    }

    @Override
    public SmsPreSendVo preSend(String target) {
        return createSmsVerificationCode(target);
    }

    @Override
    public SmsPreSendVo preSend() {
        return createSmsVerificationCode(null);
    }

    /**
     * 创建短信验证码 同时创建图片验证码
     *
     * @param target 发送目标 可为空
     * @return SmsPreSendVo
     */
    private SmsPreSendVo createSmsVerificationCode(String target) {
        final String text = kaptchaProducer.createText();

        final SmsVerificationCode verificationCode = emptyCode();
        verificationCode.setTarget(target);
        verificationCode.setPreVerificationCode(text);
        final byte[] imageBytes = createVerificationCode(text);
        final String imageBase64 = Base64Utils.encodeToString(imageBytes);
        final SmsVerificationCode saveRes = save(verificationCode);

        final SmsPreSendVo res = new SmsPreSendVo();
        res.setImageBase64(imageBase64);
        res.setTraceNo(saveRes.getTraceNo());
        return res;
    }

    /**
     * 创建一个空的验证码对象
     *
     * @return SmsVerificationCode
     */
    private SmsVerificationCode emptyCode() {
        final SmsVerificationCode code = new SmsVerificationCode();
        code.setTraceNo(RandomUtils.randomPrettyUUIDString());
        code.setExpireIn(DEFAULT_EXPIRE_IN);
        code.setState(SmsVerificationCode.STATE_CREATE);
        return code;
    }

    /**
     * 创建验证码图片
     *
     * @param text 验证码文本
     * @return 图片二进制内容化
     */
    private byte[] createVerificationCode(String text) {
        final BufferedImage image = kaptchaProducer.createImage(text);
        return CommonIOUtils.asByteArray(out -> {
            try {
                ImageIO.write(image, VERIDICATION_CODE_IMAGE_FORMAT, out);
            } catch (IOException e) {
                throw new InternalServerErrorException("Could not write image as bytes!", e);
            }
            return null;
        });
    }

    @Override
    public SmsVerificationCode send(String traceNo, String preVerificationCode) {
        final SmsVerificationCode code = requireByTraceNo(traceNo);
        if (TextUtils.isTrimedEmpty(code.getTarget())) {
            throw new BadRequestException("该记录尚未指定发送目标");
        }
        return send(code, preVerificationCode, code.getTarget());
    }

    @Override
    public SmsVerificationCode send(String traceNo, String preVerificationCode, String target) {
        @NotNull final SmsVerificationCode code = requireByTraceNo(traceNo);
        if (!TextUtils.isTrimedEmpty(code.getTarget())) {
            throw new BadRequestException("该发送记录已经指定过发送目标！");
        }
        return send(code, preVerificationCode, target);
    }

    private SmsVerificationCode send(SmsVerificationCode code, String preVerificationCode, String target) {
        if (!Objects.equals(code.getState(), SmsVerificationCode.STATE_CREATE)) {
            throw new BadRequestException("该记录已过期，请重新创建");
        }
        if (!code.getPreVerificationCode().equalsIgnoreCase(preVerificationCode)) {
            code.setState(SmsVerificationCode.STATE_ERROR);
            updateById(code);
            throw new BadRequestException("验证码错误");
        }
        code.setTarget(target);
        final String verificationCode = RandomUtils.randomString(SMS_VERIFICATION_CODE_LENGTH, RandomUtils.NUMBERS);
        sendVerificationCode(code.getTarget(), verificationCode);
        code.setState(SmsVerificationCode.SEND_OUT);
        code.setVerificationCode(verificationCode);
        final SmsVerificationCode updateRes = updateById(code);
        updateRes.setVerificationCode(null);
        return updateRes;
    }

    /**
     * 发送短信验证码
     *
     * @param target 发送目标
     * @param code   验证码
     */
    private void sendVerificationCode(String target, String code) {
        final String content = String.format(VERIFICATION_CODE_SMS_TEMPLATE, code);
        SmsSingleSend res = yunPian.singleSend(target, content);
        log.debug("Verification code send result:[{}]", res);
    }

    @Override
    public SmsVerificationCode findByTraceNo(String traceNo) {
        final SmsVerificationCode query = new SmsVerificationCode();
        query.setTraceNo(traceNo);
        return findOneByQuery(query);
    }

    @Override
    public boolean verify(String traceNo, String verificationCode) {
        final SmsVerificationCode code = requireByTraceNo(traceNo);
        return Objects.equals(code.getVerificationCode(), verificationCode);
    }

    @Override
    public @NotNull SmsVerificationCode requireByTraceNo(String traceNo) {
        final SmsVerificationCode code = findByTraceNo(traceNo);
        if (code == null) {
            throw new ResourceNotFoundException(SmsVerificationCode.class, traceNo);
        }
        return code;
    }

    @Override
    public SmsVerificationCode verifyAndGet(String traceNo, String verificationCode) {
        val smsCode = requireByTraceNo(traceNo);
        if (Objects.equals(smsCode.getVerificationCode(), verificationCode)) {
            return smsCode;
        }
        throw new BadRequestException("验证码错误！");
    }
}
