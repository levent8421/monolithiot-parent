package com.monolithiot.iot.service.encrypt.conf;

import com.monolithiot.iot.service.encrypt.PasswordEncryptor;
import com.monolithiot.iot.service.encrypt.impl.PasswordEncryptorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by 郭文梁 2019/6/18 0018 15:06
 * PasswordEncoderConfiguration
 * 密码编解码器的自动配置类
 *
 * @author 郭文梁
 * @data 2019/6/18 0018
 */
@Configuration
public class PasswordEncoderConfiguration {
    /**
     * 密码编解码器Bean
     *
     * @return encryptor
     */
    @Bean
    public PasswordEncryptor passwordEncryptor() {
        return new PasswordEncryptorImpl();
    }
}
