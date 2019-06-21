package com.monolithiot.iot.notification.api.conf;

import com.yunpian.sdk.YunpianClient;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Create by 郭文梁 2019/6/21 0021 11:38
 * YunPianConfiguration
 * 云片短信API自动化配置类
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
@Component
@ConfigurationProperties(prefix = "sms")
public class YunPianConfiguration {
    @Setter
    private String apiKey;

    /**
     * 构建云片客户端
     *
     * @return 云片客户端
     */
    @Bean
    public YunpianClient yunpianClient() {
        YunpianClient client = new YunpianClient(apiKey);
        return client.init();
    }
}
