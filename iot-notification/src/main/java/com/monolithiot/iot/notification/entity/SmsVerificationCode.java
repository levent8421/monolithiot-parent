package com.monolithiot.iot.notification.entity;

import com.monolithiot.iot.commons.entity.AbstractIntegerIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create by 郭文梁 2019/6/20 0020 17:07
 * SmsVerificationCode
 * 短信验证码实体类
 *
 * @author 郭文梁
 * @data 2019/6/20 0020
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_sms_verification_code")
public class SmsVerificationCode extends AbstractIntegerIdEntity {
    /**
     * 状态：创建
     */
    public static final int STATE_CREATE = 0x00;
    /**
     * 状态：已发送
     */
    public static final int SEND_OUT = 0x01;
    /**
     * 状态：已验证
     */
    public static final int STATE_VERIFIED = 0x02;
    /**
     * 状态：出错
     */
    public static final int STATE_ERROR = 0x03;
    /**
     * 记录号
     */
    @Column(name = "trace_no", nullable = false)
    private String traceNo;
    /**
     * 发送目标
     */
    @Column(name = "target", nullable = false)
    private String target;
    /**
     * 用户ID
     */
    @Column(name = "user_id", length = 10)
    private Integer userId;
    /**
     * 验证码内容
     */
    @Column(name = "verification_code", length = 100)
    private String verificationCode;
    /**
     * 预验证图片验证码
     */
    @Column(name = "pre_verification_code", length = 100)
    private String preVerificationCode;
    /**
     * 扩展数据
     */
    @Column(name = "extend_data")
    private String extendData;
    /**
     * 有效时间
     */
    @Column(name = "expire_in", length = 10)
    private Integer expireIn;
    /**
     * 状态
     */
    @Column(name = "state", length = 2, nullable = false)
    private Integer state;
}
