package com.monolithiot.iot.iotzuul.fileter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.iotzuul.prop.AuthorizationProp;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import java.util.List;

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
    private final AuthorizationProp authorizationProp;
    private final PathMatcher pathMatcher;
    private final ObjectMapper objectMapper;

    public AccessTokenFilter(AuthorizationProp authorizationProp,
                             PathMatcher pathMatcher,
                             ObjectMapper objectMapper) {
        this.authorizationProp = authorizationProp;
        this.pathMatcher = pathMatcher;
        this.objectMapper = objectMapper;
    }

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
        final String requestPath = getRequestPath();
        if (isAuthorized(requestPath)) {
            log.debug("Request [{}] is early authorized!", requestPath);
        } else {
            log.debug("Request [{}] is not authorized!", requestPath);
            sendPermissionDeniedMsg();
        }
        return null;
    }

    /**
     * 发送未授权响应
     *
     * @throws ZuulException ZuulException
     */
    private void sendPermissionDeniedMsg() throws ZuulException {
        GeneralResult<?> data = GeneralResult.permissionDenied("API UNAUTHORIZED");
        sendJsonResponse(HttpStatus.SC_UNAUTHORIZED, data, objectMapper);
    }

    /**
     * 判断路径是否已授权
     *
     * @param requestPath 请求路径
     * @return 是否已授权
     */
    private boolean isAuthorized(String requestPath) {
        final List<String> authorizedPath = authorizationProp.getAuthorizedPath();
        if (authorizedPath == null || authorizedPath.size() <= 0) {
            return false;
        }
        for (String pattern : authorizedPath) {
            if (pathMatcher.match(pattern, requestPath)) {
                return true;
            }
        }
        return false;
    }

}
