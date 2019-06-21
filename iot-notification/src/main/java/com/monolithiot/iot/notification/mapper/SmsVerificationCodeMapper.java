package com.monolithiot.iot.notification.mapper;

import com.monolithiot.iot.notification.entity.SmsVerificationCode;
import com.monolithiot.iot.repository.AbstractMapper;
import org.springframework.stereotype.Repository;

/**
 * Create by 郭文梁 2019/6/21 0021 11:29
 * SmsVerificationCodeMapper
 * 短信验证码相关数据库访问控制器
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
@Repository
public interface SmsVerificationCodeMapper extends AbstractMapper<SmsVerificationCode> {
}
