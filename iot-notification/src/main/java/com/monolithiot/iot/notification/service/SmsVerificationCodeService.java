package com.monolithiot.iot.notification.service;

import com.monolithiot.iot.notification.entity.SmsVerificationCode;
import com.monolithiot.iot.notification.vo.SmsPreSendVo;
import com.monolithiot.iot.service.basic.AbstractService;

import javax.validation.constraints.NotNull;

/**
 * Create by 郭文梁 2019/6/21 0021 11:33
 * SmsVerificationCodeService
 * 短信验证码相关业务行为定义
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
public interface SmsVerificationCodeService extends AbstractService<SmsVerificationCode> {
    /**
     * 短信验证码预发送
     *
     * @param target 发送目标
     * @return 预发送结果
     */
    SmsPreSendVo preSend(String target);

    /**
     * 短信预发送 不指定短信发送目标
     *
     * @return 发送结果
     */
    SmsPreSendVo preSend();

    /**
     * 发送短信验证码
     *
     * @param traceNo             记录号
     * @param preVerificationCode 预发送图片验证码内容
     * @return SmsVerificationCode
     */
    SmsVerificationCode send(String traceNo, String preVerificationCode);

    /**
     * 发送短信验证码 发送时指定发送目标
     *
     * @param traceNo             记录号
     * @param preVerificationCode 图片验证码内容
     * @param target              发送目标
     * @return SmsVerificationCode Entity
     */
    SmsVerificationCode send(String traceNo, String preVerificationCode, String target);

    /**
     * 通过记录号查询发送记录
     *
     * @param traceNo 记录号
     * @return SmsVerificationCode
     */
    SmsVerificationCode findByTraceNo(String traceNo);

    /**
     * 检查短信验证码
     *
     * @param traceNo          记录号
     * @param verificationCode 验证码
     * @return 是否匹配
     */
    boolean verify(String traceNo, String verificationCode);

    /**
     * 通过traceNo查找对象 不存在则抛出异常
     *
     * @param traceNo traceNo
     * @return entity
     */
    @NotNull
    SmsVerificationCode requireByTraceNo(String traceNo);

    /**
     * 验证并获取验证码对象
     *
     * @param traceNo          TradeId
     * @param verificationCode 验证码
     * @return SmsVerificationCode
     */
    SmsVerificationCode verifyAndGet(String traceNo, String verificationCode);
}
