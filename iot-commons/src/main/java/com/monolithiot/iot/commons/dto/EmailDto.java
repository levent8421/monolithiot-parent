package com.monolithiot.iot.commons.dto;

import lombok.Data;

/**
 * Create by 郭文梁 2019/9/10 9:23
 * EmailDto
 * Email data transaction Object
 *
 * @author 郭文梁
 * @data 2019/9/10 9:23
 */
@Data
public class EmailDto {
    /**
     * 意图：注册
     */
    public static final int INTENTION_REGISTER = 0x01;
    /**
     * 意图：更新邮箱地址
     */
    public static final int INTENTION_UPDATE_EMAIL = 0x02;
    private String target;
    private String subject;
    private Integer userId;
    private Integer intention;
}
