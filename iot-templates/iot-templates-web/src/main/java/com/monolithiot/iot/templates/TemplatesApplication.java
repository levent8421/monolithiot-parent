package com.monolithiot.iot.templates;

import com.monolithiot.iot.templates.context.TemplatesConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Create By leven ont 2019/7/16 22:22
 * Class Name :[TemplatesApplication]
 * 模板服务启动入口
 *
 * @author leven
 */
@MapperScan({TemplatesConstants.Context.MAPPER_PACKAGE_NAME})
@SpringBootApplication(scanBasePackages = TemplatesConstants.Context.COMPONENT_BASE_PACKAGE)
@EnableDiscoveryClient
public class TemplatesApplication {
    /**
     * 启动主方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(TemplatesApplication.class, args);
    }
}
