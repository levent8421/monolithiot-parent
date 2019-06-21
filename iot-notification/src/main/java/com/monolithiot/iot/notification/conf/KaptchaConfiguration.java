package com.monolithiot.iot.notification.conf;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Create by 郭文梁 2019/6/21 0021 13:13
 * KaptchaConfiguration
 * 图片验证码配置
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
@Configuration
public class KaptchaConfiguration {
    /**
     * 配置验证码生成器
     *
     * @return 生成器
     */
    @Bean
    public Producer kaptchaProduce() {
        Properties prop = new Properties();
        prop.setProperty("kaptcha.border.color", "255,239,128");
        prop.setProperty("kaptcha.textproducer.font.color", "89,99,100");
        prop.setProperty("kaptcha.image.width", "100");
        prop.setProperty("kaptcha.image.height", "50");
        prop.setProperty("kaptcha.textproducer.font.size", "45");
        prop.setProperty("kaptcha.textproducer.char.length", "4");
        prop.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        prop.setProperty("kaptcha.textproducer.char.string", "23456789ABCEFGHJKLMNPQRSTUVWXYZ");
        prop.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
        prop.setProperty("kaptcha.noise.color", "200,100,0");
        prop.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
        prop.setProperty("kaptcha.background.clear.from", "149,228,239");
        prop.setProperty("kaptcha.background.clear.to", "white");
        prop.setProperty("kaptcha.textproducer.char.space", "3");
        Config config = new Config(prop);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
