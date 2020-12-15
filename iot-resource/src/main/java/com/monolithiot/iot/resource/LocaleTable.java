package com.monolithiot.iot.resource;

import java.util.HashMap;
import java.util.Locale;

/**
 * Create By Levent8421
 * Create Time: 2020/12/15 13:25
 * Class Name: LocaleTable
 * Author: Levent8421
 * Description:
 * Locale table
 *
 * @author Levent8421
 */

public class LocaleTable extends HashMap<String, Locale> {
    private static final LocaleTable INSTANCE = new LocaleTable();
    public static final String ZH_CN = "zh_CN";
    public static final String ZH_TW = "zh_TW";
    public static final String EN_US = "en_US";
    public static final String EN_UK = "en_UK";

    public static LocaleTable getInstance() {
        return INSTANCE;
    }

    private LocaleTable() {
        put(ZH_CN, Locale.SIMPLIFIED_CHINESE);
        put(ZH_TW, Locale.TRADITIONAL_CHINESE);
        put(EN_US, Locale.US);
        put(EN_UK, Locale.UK);
    }

    public Locale getDefault() {
        return get(ZH_CN);
    }

    @Override
    public Locale get(Object key) {
        final Locale locale = super.get(key);
        if (locale == null) {
            throw new IllegalArgumentException("Can not find locale for name " + key);
        }
        return locale;
    }
}
