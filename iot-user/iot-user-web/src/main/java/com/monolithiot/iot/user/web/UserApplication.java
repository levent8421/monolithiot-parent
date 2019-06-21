package com.monolithiot.iot.user.web;

import com.monolithiot.iot.commons.context.ApplicationConstants;
import com.monolithiot.iot.user.context.UserServiceConstants;
import com.monolithiot.iot.user.entity.UserConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Create By leven ont 2019/6/15 22:39
 * Class Name :[UserApplication]
 * <p>
 * 启动类
 *
 * @author leven
 */
@MapperScan({UserConstants.Context.MAPPER_PACKAGE_NAME})
@SpringBootApplication(scanBasePackages = ApplicationConstants.Context.COMPONENT_PACKAGE_NAME)
@EnableFeignClients(basePackages = UserServiceConstants.Context.FEIGN_CLIENT_PACKAGE)
public class UserApplication {
    /**
     * 启动方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
