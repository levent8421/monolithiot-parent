package com.monolithiot.iot.notification.api.impl;

import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import com.monolithiot.iot.notification.api.YunPian;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Create by 郭文梁 2019/6/15 0015 13:17
 * YunPianImpl
 * 云片客户端实现
 *
 * @author 郭文梁
 * @data 2019/6/15 0015
 */
@Component
@Slf4j
public class YunPianImpl implements YunPian {
    private final YunpianClient yunpianClient;

    public YunPianImpl(YunpianClient yunpianClient) {
        this.yunpianClient = yunpianClient;
    }

    /**
     * 发送一条短信
     *
     * @param to  发送到
     * @param msg 消息
     * @return 发送结果
     */
    @Override
    public SmsSingleSend singleSend(String to, String msg) {
        final Map<String, String> requestParams = yunpianClient.newParam(2);
        requestParams.put(YunpianClient.MOBILE, to);
        requestParams.put(YunpianClient.TEXT, msg);
        final Result<SmsSingleSend> res = yunpianClient.sms().single_send(requestParams);
        if (!res.isSucc()) {
            throw new InternalServerErrorException(res.getMsg());
        }
        return res.getData();
    }
}
