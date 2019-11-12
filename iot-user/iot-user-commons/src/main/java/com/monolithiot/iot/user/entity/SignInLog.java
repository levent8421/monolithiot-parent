package com.monolithiot.iot.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.monolithiot.iot.commons.context.ApplicationConstants;
import com.monolithiot.iot.commons.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by 郭文梁 2019/11/11 18:05
 * SignInLog
 * User SignIn Log
 *
 * @author 郭文梁
 * @data 2019/11/11 18:05
 */
@Table(name = "t_sign_in_log")
@EqualsAndHashCode(callSuper = true)
@Data
public class SignInLog extends AbstractEntity<Integer> {
    /**
     * 用户ID
     */
    @Column(name = "user_id", length = 10, nullable = false)
    private Integer userId;
    /**
     * 登录日期
     */
    @JsonFormat(pattern = ApplicationConstants.DateTime.DATE_FORMATER)
    @Column(name = "sign_in_date")
    private Date signInDate;
}
