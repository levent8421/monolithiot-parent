package com.monolithiot.iot.iotzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Create By leven ont 2019/6/16 15:42
 * Class Name :[IotZuulApplication]
 * API网关
 *
 * @author leven
 */
@EnableDiscoveryClient
@SpringBootApplication
public class IotZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(IotZuulApplication.class, args);
    }
}
