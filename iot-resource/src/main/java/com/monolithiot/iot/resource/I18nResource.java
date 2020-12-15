package com.monolithiot.iot.resource;

import java.util.Locale;

/**
 * Create By Levent8421
 * Create Time: 2020/12/14 20:45
 * Class Name: I18nResource
 * Author: Levent8421
 * Description:
 * 多语言资源
 *
 * @author Levent8421
 */
public interface I18nResource {
    String PASSWORD_ERROR       = "user.password_error";
    String DUPLICATE_PHONE      = "user.duplicate_phone";
    String DUPLICATE_EMAIL      = "user.duplicate_email";
    String DUPLICATE_NAME       = "user.duplicate_name";
    String BAD_VERIFY_CODE      = "user.bad_verify_code";
    String ERROR_ON_SAVE_AVATAR = "user.error_on_save_avatar";
    String INVALIDATE_LINK      = "email.invalidate_link";
    String INVALIDATE_EMAIL     = "email.invalidate_email";
    String NO_PARAMS            = "validate.no_params";
    String VALIDATE_REQUIRE     = "validate.require";

    /**
     * 获取资源文本
     *
     * @param id          id
     * @param locale      地区
     * @param defaultText 默认值
     * @param args        参数
     * @return 资源
     */
    String getText(String id, Locale locale, String defaultText, Object... args);

    /**
     * 获取字符串资源
     *
     * @param id     资源ID
     * @param locale 地区
     * @param args   参数
     * @return 资源
     */
    String getText(String id, Locale locale, Object... args);

    /**
     * 获取字符串资源
     *
     * @param id   资源ID
     * @param args 参数
     * @return 资源
     */
    String getText(String id, Object... args);
}
