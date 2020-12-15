package com.monolithiot.iot.resource.locale;

import com.monolithiot.iot.resource.LocaleTable;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Create By Levent8421
 * Create Time: 2020/12/15 14:37
 * Class Name: LocaleHolder
 * Author: Levent8421
 * Description:
 * Local Holder
 *
 * @author Levent8421
 */
@Component
public class LocaleHolder {
    private final ThreadLocal<Locale> localeCache = new ThreadLocal<>();

    public void setLocale(Locale locale) {
        localeCache.set(locale);
    }

    public Locale getLocale() {
        final Locale locale = localeCache.get();
        return locale == null ? LocaleTable.getInstance().getDefault() : locale;
    }

    public boolean isPresent() {
        return localeCache.get() != null;
    }

    public void clear() {
        localeCache.remove();
    }
}
