package com.monolithiot.iot.notification;

import com.monolithiot.iot.commons.context.ApplicationConstants;
import com.monolithiot.iot.notification.context.NotificationConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Create by 郭文梁 2019/6/20 0020 15:10
 * NotificationApplication
 * 通知服务启动类
 *
 * @author 郭文梁
 * @data 2019/6/20 0020
 */
@MapperScan(basePackages = NotificationConstants.Context.MAPPER_PACKAGE)
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = ApplicationConstants.Context.COMPONENT_PACKAGE_NAME)
public class NotificationApplication {
    /**
     * 启动方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
}
