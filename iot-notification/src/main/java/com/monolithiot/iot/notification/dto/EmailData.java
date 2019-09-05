package com.monolithiot.iot.notification.dto;

import lombok.Data;

/**
 * Create by 郭文梁 2019/9/5 18:42
 * EmailData
 * Email Data
 *
 * @author 郭文梁
 * @data 2019/9/5 18:42
 */
@Data
public class EmailData {
    /**
     * Send target
     */
    private String target;
    /**
     * Email Subject
     */
    private String subject;
    /**
     * Email Content
     */
    private String content;
}
