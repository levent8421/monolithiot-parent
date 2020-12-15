package com.monolithiot.iot.user.web.interceptor;

import com.monolithiot.iot.commons.context.ApplicationConstants;
import com.monolithiot.iot.resource.LocaleTable;
import com.monolithiot.iot.resource.locale.LocaleHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Create By Levent8421
 * Create Time: 2020/12/15 14:36
 * Class Name: LocaleHeaderInterceptor
 * Author: Levent8421
 * Description:
 * Locale Header Reader
 *
 * @author Levent8421
 */
@Component
@Slf4j
public class LocaleHeaderInterceptor implements HandlerInterceptor {
    private final LocaleHolder localeHolder;

    public LocaleHeaderInterceptor(LocaleHolder localeHolder) {
        this.localeHolder = localeHolder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String localeName = request.getHeader(ApplicationConstants.Router.LOCALE_NAME);
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            final String value = request.getHeader(headerName);
            log.debug("Request Header: [{}]:[{}]", headerName, value);
        }
        Locale locale;
        try {
            locale = LocaleTable.getInstance().get(localeName);
            log.debug("Save locale [{}/{}] into holder!", locale.getCountry(), locale.getLanguage());
        } catch (Exception e) {
            locale = LocaleTable.getInstance().getDefault();
            log.debug("Save default locale [{}/{}] into holder, sourceName=[{}]!", locale.getCountry(), locale.getLanguage(), localeName);
        }
        localeHolder.setLocale(locale);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        localeHolder.clear();
    }
}
