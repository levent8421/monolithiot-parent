package com.monolithiot.iot.notification.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Create by 郭文梁 2019/9/6 9:37
 * EmailConfiguration
 * Email Auto Configuration Class
 *
 * @author 郭文梁
 * @data 2019/9/6 9:37
 */
@Slf4j
@Component
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfiguration {
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public EmailConfiguration(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    /**
     * Freemarker Template Configuration bean
     *
     * @return bean
     */
    @Bean(name = "templateConfiguration")
    public freemarker.template.Configuration templateConfiguration() {
        return freeMarkerConfigurer.getConfiguration();
    }
}
