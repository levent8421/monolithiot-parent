package com.monolithiot.iot.iotzuul.fileter;

import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Create By leven ont 2019/6/16 22:09
 * Class Name :[AccessTokenFilter]
 * <p>
 * 令牌预处理过滤器
 *
 * @author leven
 */
@Component
@Slf4j
public class AccessTokenFilter extends AbstractZuulFilter {
    /**
     * 过滤器排序
     */
    private static final int FILTER_ORDER = 100;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return isSendZuulResponse();
    }

    @Override
    public Object run() throws ZuulException {
        log.debug("Request Params= [{}]", getRequestParams());
        log.debug("Header (User-Agent) = [{}]", getHeader("User-Agent"));
        return null;
    }
}
