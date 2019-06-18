package com.monolithiot.iot.iotzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Create By leven ont 2019/6/16 15:42
 * Class Name :[IotZuulApplication]
 * API网关
 * <p>
 * Note:
 * 1.<code>exclude = DataSourceAutoConfiguration.class</code>
 * 原因：Zuul网关不直接连接数据库，但是需要使用数据库的一些组件包，故禁用数据库Datasource的自动配置
 *
 * @author leven
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class IotZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(IotZuulApplication.class, args);
    }
}
