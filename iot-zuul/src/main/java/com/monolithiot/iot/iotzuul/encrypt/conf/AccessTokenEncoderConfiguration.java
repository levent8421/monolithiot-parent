package com.monolithiot.iot.iotzuul.encrypt.conf;

import com.monolithiot.iot.iotzuul.encrypt.AccessTokenEncoder;
import com.monolithiot.iot.iotzuul.encrypt.impl.AccessTokenEncoderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by 郭文梁 2019/6/18 0018 17:21
 * AccessTokenEncoderConfiguration
 * 令牌编解码器的自动配置类
 *
 * @author 郭文梁
 * @data 2019/6/18 0018
 */
@Configuration
public class AccessTokenEncoderConfiguration {
    /**
     * AccessToken编解码器的Bean
     *
     * @return bean
     */
    @Bean
    public AccessTokenEncoder accessTokenEncoder() {
        return new AccessTokenEncoderImpl();
    }
}
