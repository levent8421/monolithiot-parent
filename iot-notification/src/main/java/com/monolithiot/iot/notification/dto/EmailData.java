package com.monolithiot.iot.notification.dto;

import lombok.Data;

import javax.mail.internet.InternetAddress;
import java.io.Serializable;

/**
 * Create by 郭文梁 2019/9/5 18:42
 * EmailData
 * Email Data
 *
 * @author 郭文梁
 * @data 2019/9/5 18:42
 */
@Data
public class EmailData implements Serializable {
    /**
     * User Id
     */
    private Integer userId;
    /**
     * Trace Id
     */
    private String traceId;
    /**
     * Email from
     */
    private InternetAddress from;
    /**
     * Send target
     */
    private String target;
    /**
     * Email Subject
     */
    private String subject;
    /**
     * Email Content template name
     */
    private String templateName;
    /**
     * Model object
     */
    private Object dataModel;
    /**
     * Intention
     */
    private Integer intention;
    /**
     * Render result
     */
    private String contentText;
}
