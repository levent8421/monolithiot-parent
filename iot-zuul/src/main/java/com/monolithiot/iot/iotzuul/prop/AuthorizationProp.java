package com.monolithiot.iot.iotzuul.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Create By leven ont 2019/6/17 23:16
 * Class Name :[AuthorizationProp]
 * <p>
 * 授权相关配置
 *
 * @author leven
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthorizationProp {
    /**
     * 已授权路径
     */
    private List<String> authorizedPath;
}
