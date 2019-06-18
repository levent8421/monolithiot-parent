package com.monolithiot.iot.user.context;

/**
 * Create By leven ont 2019/6/18 22:26
 * Class Name :[UserServiceConstants]
 * <p>
 * 用户服务相关常量
 *
 * @author leven
 */
public class UserServiceConstants {
    /**
     * 上下文相关常量
     */
    public static class Context {
        /**
         * 系统内部API Controller包
         */
        public static final String API_CONTROLLER_PACKAGE = "com.monolithiot.iot.user.web.controller.api";
        /**
         * 开放访问API Controller包
         */
        public static final String OPEN_CONTROLLER_PACKAGE = "com.monolithiot.iot.user.web.controller.safe";
        /**
         * 有访问限制的API Controller包
         */
        public static final String SAFE_CONTROLLER_PACKAGE = "com.monolithiot.iot.user.web.controller.open";
    }
}
