package com.monolithiot.iot.user.web.vo;

import lombok.Data;

/**
 * Create by 郭文梁 2019/9/3 19:49
 * UpdatePhoneParam
 * 更新电话号的参数
 *
 * @author 郭文梁
 * @data 2019/9/3 19:49
 */
@Data
public class UpdatePhoneParam {
    /**
     * 验证码记录ID
     */
    private String smsTraceId;
    /**
     * 验证码
     */
    private String verificationCode;
}
