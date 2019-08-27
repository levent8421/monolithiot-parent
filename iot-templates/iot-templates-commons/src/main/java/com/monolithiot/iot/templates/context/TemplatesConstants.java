package com.monolithiot.iot.templates.context;

/**
 * Create By leven ont 2019/7/16 22:23
 * Class Name :[TemplatesConstants]
 * 模板服务相关常量
 *
 * @author leven
 */
public class TemplatesConstants {
    /**
     * 上下文相关常量
     */
    public static class Context {
        public static final String COMPONENT_BASE_PACKAGE = "com.monolithiot.iot.templates";
        public static final String MAPPER_PACKAGE_NAME = "com.monolithiot.iot.templates.repository.sql";
        public static final String SAFE_CONTROLLER_PACKAGE = "com.monolithiot.iot.templates.controller.safe";
        public static final String API_CONTROLLER_PACKAGE = "com.monolithiot.iot.templates.controller.api";
    }

    /**
     * ES相关常量
     */
    public static class ElasticSearch {
        /**
         * 测量数据Index
         */
        public static final String MEASURE_DATA_INDEX = "measure_data_index_2";
        /**
         * 测量数据Type
         */
        public static final String MEASURE_DATA_TYPE = "measure_data";
    }
}
