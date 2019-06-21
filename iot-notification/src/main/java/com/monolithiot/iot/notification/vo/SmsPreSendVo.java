package com.monolithiot.iot.notification.vo;

import lombok.Data;

/**
 * Create by 郭文梁 2019/6/21 0021 13:32
 * SmsPreSendVo
 * 短信验证码预发送返回结果
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
@Data
public class SmsPreSendVo {
    /**
     * 流水号
     */
    private String traceNo;
    /**
     * 图片内容 base64编码结果
     */
    private String imageBase64;
}
