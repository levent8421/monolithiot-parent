package com.monolithiot.iot.iotzuul.fileter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolithiot.iot.commons.context.ApplicationConstants;
import com.monolithiot.iot.commons.dto.UserLoginDto;
import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.commons.utils.HttpRequestUtils;
import com.monolithiot.iot.commons.utils.TextUtils;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.iotzuul.encrypt.AccessTokenEncoder;
import com.monolithiot.iot.iotzuul.feign.UserFeignClient;
import com.monolithiot.iot.iotzuul.prop.AuthorizationProp;
import com.monolithiot.iot.resource.I18nResource;
import com.monolithiot.iot.resource.LocaleTable;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
    /**
     * 令牌字符串前缀
     */
    private static final String ACCESS_TOKEN_PREFIX = "token/";
    private final AuthorizationProp authorizationProp;
    private final PathMatcher pathMatcher;
    private final ObjectMapper objectMapper;
    private final UserFeignClient userFeignClient;
    private final AccessTokenEncoder accessTokenEncoder;
    private final I18nResource i18nResource;


    public AccessTokenFilter(AuthorizationProp authorizationProp,
                             PathMatcher pathMatcher,
                             ObjectMapper objectMapper,
                             UserFeignClient userFeignClient,
                             AccessTokenEncoder accessTokenEncoder,
                             I18nResource i18nResource) {
        this.authorizationProp = authorizationProp;
        this.pathMatcher = pathMatcher;
        this.objectMapper = objectMapper;
        this.userFeignClient = userFeignClient;
        this.accessTokenEncoder = accessTokenEncoder;
        this.i18nResource = i18nResource;
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
        readLocaleIntoRequest();
        final String requestPath = getRequestPath();
        if (doLoginFilter(requestPath)) {
            //执行登陆操作
            //若该路径不是登录路径 则继续向下执行
            return null;
        }
        if (isAuthorized(requestPath)) {
            log.debug("Request [{}] is early authorized!", requestPath);
        } else {
            log.debug("Request [{}] is not authorized!", requestPath);
            AccessToken accessToken = checkAccessToken(currentContext().getRequest());
            if (accessToken == null) {
                sendPermissionDeniedMsg();
            } else {
                writeAccessToken2Request(accessToken);
            }
        }
        return null;
    }

    private void readLocaleIntoRequest() {
        final RequestContext requestContext = currentContext();
        final String locale = requestContext.getRequest().getHeader(ApplicationConstants.Http.LOCALE_HEADER_NAME);
        log.debug("Set local name [{}] into request!", locale);
        requestContext.addZuulRequestHeader(ApplicationConstants.Router.LOCALE_NAME, locale);
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

    /**
     * 执行登陆拦截
     * 当当前路径为配置的登录路径并且请求方式为Post时，执行登陆拦截，并返回true
     *
     * @param path 请求路径
     * @return 是否成功拦截
     * @throws ZuulException Zuu;异常
     */
    private boolean doLoginFilter(String path) throws ZuulException {
        final String loginPath = authorizationProp.getLoginPath();
        final HttpServletRequest request = currentContext().getRequest();
        final String method = request.getMethod();
        if (pathMatcher.match(loginPath, path) && HttpMethod.POST.matches(method)) {
            final String requestBody = HttpRequestUtils.stringBody(request);
            final String localeName = request.getHeader(ApplicationConstants.Http.LOCALE_HEADER_NAME);
            Locale locale;
            try {
                locale = LocaleTable.getInstance().get(localeName);
            } catch (Exception e) {
                locale = LocaleTable.getInstance().getDefault();
            }
            doLogin(requestBody, locale, localeName);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 执行登陆操作
     *
     * @param requestBody 请求体
     * @param locale      locale
     * @throws ZuulException Zuu;异常
     */
    private void doLogin(String requestBody, Locale locale, String localeName) throws ZuulException {
        UserLoginDto userLoginDto = UserLoginDto.fromJson(requestBody);
        userLoginDto.setLocale(localeName);
        if (checkLoginParam(userLoginDto, locale)) {
            final GeneralResult<AccessToken> res = userFeignClient.login(userLoginDto);
            if (Objects.equals(res.getCode(), HttpStatus.SC_OK)) {
                final AccessToken accessToken = res.getData();
                final String tokenStr = accessTokenEncoder.encode(accessToken.asJson(), ACCESS_TOKEN_PREFIX);
                sendJsonResponse(HttpStatus.SC_OK, GeneralResult.ok(tokenStr), objectMapper);
            } else {
                sendJsonResponse(res.getCode(), res, objectMapper);
            }
        }
    }

    /**
     * 检查登录参数
     *
     * @param userLoginDto 登录参数对象
     * @return 是否合法
     * @throws ZuulException ZuulException
     */
    private boolean checkLoginParam(UserLoginDto userLoginDto, Locale locale) throws ZuulException {
        if (userLoginDto == null) {
            sendJsonResponse(HttpStatus.SC_BAD_REQUEST, GeneralResult.badRequest(getResource(I18nResource.NO_PARAMS, locale)), objectMapper);
            return false;
        }
        if (TextUtils.isTrimedEmpty(userLoginDto.getLoginName())) {
            sendJsonResponse(HttpStatus.SC_BAD_REQUEST, GeneralResult.badRequest(getResource(I18nResource.VALIDATE_REQUIRE, locale, "LoginName")), objectMapper);
            return false;
        }
        if (TextUtils.isTrimedEmpty(userLoginDto.getPassword())) {
            sendJsonResponse(HttpStatus.SC_BAD_REQUEST, GeneralResult.badRequest(getResource(I18nResource.VALIDATE_REQUIRE, locale, "Password")), objectMapper);
            return false;
        }
        return true;
    }

    /**
     * 检查请求是否携带有有效的令牌,若存在 则返回，否则返回null
     *
     * @param request 请求对象
     * @return 令牌是否有效
     */
    private AccessToken checkAccessToken(HttpServletRequest request) {
        String token = request.getHeader(authorizationProp.getTokenHeaderName());
        if (TextUtils.isTrimedEmpty(token)) {
            token = request.getParameter(authorizationProp.getTokenParamName());
        }
        if (TextUtils.isTrimedEmpty(token)) {
            return null;
        }
        AccessToken accessToken = unpackAccessToken(token);
        if (accessToken == null) {
            return null;
        }
        if (!validateAccessToken(accessToken)) {
            return null;
        }
        log.debug("Request Access Token [{}]", accessToken);
        return accessToken;
    }

    /**
     * 检查令牌的有效性
     *
     * @param accessToken 令牌对象
     * @return 是否有效
     */
    private boolean validateAccessToken(AccessToken accessToken) {
        if (accessToken.getCreateTime() == null || accessToken.getExpireIn() == null) {
            return false;
        }
        if (accessToken.getCreateTime() + accessToken.getExpireIn() < System.currentTimeMillis()) {
            //令牌已过期
            return false;
        }
        //令牌角色
        return Objects.equals(accessToken.getRole(), AccessToken.ROLE_USER);
    }

    /**
     * 从token中解析出AccessToken对象
     *
     * @param token 令牌字符串
     * @return 令牌对象
     */
    private AccessToken unpackAccessToken(String token) {
        if (token.length() <= ACCESS_TOKEN_PREFIX.length()) {
            log.warn("Invalidate token length [{}], length [{}]", token, token.length());
            return null;
        }
        String tokenStr = token.substring(ACCESS_TOKEN_PREFIX.length());
        try {
            return accessTokenEncoder.decode(tokenStr, AccessToken.class);
        } catch (Throwable e) {
            log.warn("Unable to unpack tokenString [{}]", tokenStr, e);
        }
        return null;
    }

    /**
     * 写令牌内容到路由转发的请求中，供应用服务使用
     *
     * @param accessToken 令牌
     */
    private void writeAccessToken2Request(AccessToken accessToken) {
        RequestContext context = currentContext();
        context.addZuulRequestHeader(ApplicationConstants.Router.LOGIN_NAME_HEADER_NAME,
                accessToken.getLoginName());
        context.addZuulRequestHeader(ApplicationConstants.Router.USER_ID_HEADER_NAME,
                String.valueOf(accessToken.getUserId()));
    }

    private String getResource(String id, Locale local, Object... args) {
        return i18nResource.getText(id, local, args);
    }
}
