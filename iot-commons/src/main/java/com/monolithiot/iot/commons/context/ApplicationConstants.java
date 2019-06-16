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
}
