package com.monolithiot.iot.commons.vo.notification;

import lombok.Data;

/**
 * Create By leven ont 2019/6/21 21:56
 * Class Name :[VerifySmsCodeParam]
 * <p>
 * 校验短信验证码参数
 *
 * @author leven
 */
@Data
public class VerifySmsCodeParam {
    /**
     * 验证码
     */
    private String verificationCode;
    /**
     * 记录号
     */
    private String traceNo;
}
