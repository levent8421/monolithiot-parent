package com.monolithiot.iot.iotzuul.fileter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Create By leven ont 2019/6/16 22:23
 * Class Name :[AbstractZuulFilter]
 * <p>
 * 封装过滤器通用规则
 *
 * @author leven
 */
public abstract class AbstractZuulFilter extends ZuulFilter {
    /**
     * 过滤类型 ： Pre
     */
    /*package private*/ static final String PRE_TYPE = "pre";
    /**
     * 过滤类型 ： route
     */
    /*package private*/ static final String ROUTE_TYPE = "route";
    /**
     * 过滤类型 ： post
     */
    /*package private*/ static final String POST_TYPE = "post";
    /**
     * 过滤类型 ： error
     */
    /*package private*/ static final String ERROR_TYPE = "error";

    /**
     * 请求是否已经被其他过滤器处理过，即其他处理器有没有设置sendZuulReponse为True
     *
     * @return 该请求是否已经被处理
     */
    boolean isSendZuulResponse() {
        return currentContext().sendZuulResponse();
    }

    /**
     * 当前请求的上下文
     *
     * @return 请求上下文
     */
    RequestContext currentContext() {
        return RequestContext.getCurrentContext();
    }
}
