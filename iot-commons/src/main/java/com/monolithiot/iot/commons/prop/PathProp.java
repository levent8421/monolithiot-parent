package com.monolithiot.iot.commons.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create By leven ont 2019/7/16 23:00
 * Class Name :[PathProp]
 * <p>
 * 站点相关配置
 *
 * @author leven
 */
@Data
@Component
@ConfigurationProperties(prefix = "path")
public class PathProp {
    /**
     * 静态文件保存根目录
     */
    private String staticFilePath;
    /**
     * 模板图片保存路径
     */
    private String templateImagePath = "/templates/images/";
    /**
     * 静态资源服务器访问前缀
     */
    private String staticServerPrefix;
    /**
     * 用户头像保存路径
     */
    private String userAvatarPath = "/user/avatar/";
}
