package com.monolithiot.iot.commons.context;

/**
 * Create By levent ont 2019/6/14 22:39
 * Class Name :[ApplicationConstants]
 * <p>
 * 系统常量表
 *
 * @author levent
 */
public class ApplicationConstants {
    /**
     * 系统默认编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 上下文等系统运行常量
     */
    public static class Context {
        /**
         * 系统组件所在的包名
         * 系统启动后将会扫描该包下的所有合法组件类并注册为Spring组件
         */
        public static final String COMPONENT_PACKAGE_NAME = "com.monolithiot.iot";
    }

    /**
     * 时间日期相关常量
     */
    public static class DateTime {
        /**
         * 时间日期格式化样式
         */
        public static final String DATE_TIME_FORMATER = "yyyy-MM-dd HH:mm:ss";
        /**
         * 日期格式化样式
         */
        public static final String DATE_FORMATER = "yyyy-MM-dd";
        /**
         * 时间格式化样式
         */
        public static final String TIME_FORMATER = "HH:mm:ss";
    }

    /**
     * 数据库相关常量
     */
    public static class Sql {
        /**
         * MyBatis JDBC 主键生成器的名称
         */
        public static final String GENERATOR_JDBC = "JDBC";
    }

    /**
     * Http相关常量
     */
    public static class Http {
        /**
         * Json Content Type
         */
        public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
        /**
         * Locale
         */
        public static final String LOCALE_HEADER_NAME = "X-Locale";
    }

    /**
     * 路由相关常量
     */
    public static class Router {
        /**
         * 路由设置的用户登陆名Header名称
         */
        public static final String LOGIN_NAME_HEADER_NAME = "Zuul-Router-Login-Name";
        /**
         * 路由设置的用户ID Header名称
         */
        public static final String USER_ID_HEADER_NAME = "Zuul-Router-User-Id";
        /**
         * 地区
         */
        public static final String LOCALE_NAME = "Zuul-Router-Locale";
    }

    /**
     * 服务相关常量
     */
    public static class Service {
        /**
         * 用户服务
         */
        public static final String USER = "user-service";
        /**
         * 通知服务
         */
        public static final String NOTIFICATION = "notification-service";
    }

    public static class MessageQueue {
        public static final String EMAIL_QUEUE_NAME = "monolithiot.notification.email";
        public static final String EMAIL_EXCHANGE = "monolithiot.notification.email";
        public static final String EMAIL_ROUTING = "monolithiot.notification.email";
    }
}
