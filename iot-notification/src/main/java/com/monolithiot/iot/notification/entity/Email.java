package com.monolithiot.iot.notification.entity;

import com.monolithiot.iot.commons.dto.EmailDto;
import com.monolithiot.iot.commons.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create by 郭文梁 2019/9/6 9:20
 * Email
 * Email Entity
 *
 * @author 郭文梁
 * @data 2019/9/6 9:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_email")
public class Email extends AbstractEntity<Integer> {
    /**
     * 意图：注册
     */
    public static final int INTENTION_REGISTER = EmailDto.INTENTION_REGISTER;
    /**
     * 意图：更新邮箱地址
     */
    public static final int INTENTION_UPDATE_EMAIL = EmailDto.INTENTION_UPDATE_EMAIL;
    /**
     * 意图：重置密码
     */
    public static final int INTENTION_RESET_PASSWORD = EmailDto.INTENTION_RESET_PASSWORD;
    /**
     * 记录号
     */
    @Column(name = "trace_id", nullable = false)
    private String traceId;
    /**
     * 用户ID
     */
    @Column(name = "user_id", length = 10)
    private Integer userId;
    /**
     * 发送目标
     */
    @Column(name = "target", nullable = false)
    private String target;
    /**
     * 主题
     */
    @Column(name = "subject", nullable = false)
    private String subject;
    /**
     * Content text
     */
    @Column(name = "content_text")
    private String contentText;
    /**
     * Intention
     */
    @Column(name = "intention", length = 2, nullable = false)
    private Integer intention;
}
