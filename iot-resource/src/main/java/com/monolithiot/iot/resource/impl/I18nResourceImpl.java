package com.monolithiot.iot.resource.impl;

import com.monolithiot.iot.resource.I18nResource;
import com.monolithiot.iot.resource.LocaleTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Create By Levent8421
 * Create Time: 2020/12/15 11:48
 * Class Name: I18nResourceImpl
 * Author: Levent8421
 * Description:
 * 国际化资源组件
 *
 * @author Levent8421
 */
@Component
@Slf4j
public class I18nResourceImpl implements I18nResource {
    private final MessageSource messageSource;

    public I18nResourceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getText(String id, Locale locale, String defaultText, Object... args) {
        final String message = messageSource.getMessage(id, args, defaultText, locale);
        log.debug("Get resource message[{}], locale=[{}/{}], res=[{}]", id, locale.getCountry(), locale.getLanguage(), message);
        return message;
    }

    @Override
    public String getText(String id, Locale locale, Object... args) {
        final String message = messageSource.getMessage(id, args, locale);
        log.debug("Get resource message[{}], locale=[{}/{}], res=[{}]", id, locale.getCountry(), locale.getLanguage(), message);
        return message;
    }

    @Override
    public String getText(String id, Object... args) {
        final String message = messageSource.getMessage(id, args, LocaleTable.getInstance().getDefault());
        log.debug("Get resource message[{}], locale=[default], res=[{}]", id, message);
        return message;
    }
}
