package com.monolithiot.iot.notification.api;

import com.yunpian.sdk.model.SmsSingleSend;

/**
 * Create by 郭文梁 2019/6/15 0015 10:12
 * YunPian
 * 云片API
 *
 * @author 郭文梁
 * @data 2019/6/15 0015
 */
public interface YunPian {
    /**
     * 短信单条发送
     *
     * @param target  发送目标
     * @param content 发送内容
     * @return 发送结果
     */
    SmsSingleSend singleSend(String target, String content);
}
