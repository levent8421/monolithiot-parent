package com.monolithiot.iot.notification.context;

/**
 * Create by 郭文梁 2019/6/21 0021 14:04
 * NotificationConstants
 * 通知系统的相关常量
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
public class NotificationConstants {
    /**
     * 上下文常量
     */
    public static class Context {
        /**
         * mapper类所在的包
         */
        public static final String MAPPER_PACKAGE = "com.monolithiot.iot.notification.mapper";
        /**
         * 需要授权的控制器报名
         */
        public static final String SAFE_CONTROLLER_PACKAGE = "com.monolithiot.iot.notification.controller.safe";
        /**
         * 开放访问的控制器报名
         */
        public static final String OPEN_CONTROLLER_PACKAGE = "com.monolithiot.iot.notification.controller.open";
        /**
         * 系统内API访问控制器的包名
         */
        public static final String API_CONTROLLER_PACKAGE = "com.monolithiot.iot.notification.controller.api";
    }
}
