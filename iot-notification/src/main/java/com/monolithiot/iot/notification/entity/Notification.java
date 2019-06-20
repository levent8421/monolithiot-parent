package com.monolithiot.iot.notification.entity;

import com.monolithiot.iot.commons.entity.AbstractIntegerIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create by 郭文梁 2019/6/20 0020 15:17
 * Notification
 * 通知记录
 *
 * @author 郭文梁
 * @data 2019/6/20 0020
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_notification")
public class Notification extends AbstractIntegerIdEntity {
    /**
     * 通知类型：短信通知
     */
    public static final int TYPE_SMS = 0x01;
    /**
     * 通知类型：邮件通知
     */
    public static final int TYPE_EMAIL = 0x02;
    /**
     * 记录号
     */
    @Column(name = "trace_no", nullable = false)
    private String traceNo;
    /**
     * 通知类型
     */
    @Column(name = "type", length = 2, nullable = false)
    private Integer type;
    /**
     * 通知目标
     */
    @Column(name = "target")
    private String target;
    /**
     * 用户ID
     */
    @Column(name = "user_id", length = 10)
    private Integer userId;
    /**
     * 验证码， 发送内容为验证码时存在
     */
    @Column(name = "code")
    private String code;
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
}
