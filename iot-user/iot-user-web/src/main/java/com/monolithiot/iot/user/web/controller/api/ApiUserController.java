package com.monolithiot.iot.user.web.controller.api;

import com.monolithiot.iot.commons.dto.UserLoginDto;
import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.commons.utils.ParamChecker;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.resource.I18nResource;
import com.monolithiot.iot.resource.LocaleTable;
import com.monolithiot.iot.resource.locale.LocaleHolder;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.general.UserService;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * Create by 郭文梁 2019/6/18 0018 18:05
 * ApiUserController
 * 用户Api相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/6/18 0018
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class ApiUserController extends AbstractEntityController<User> {
    private final UserService userService;
    private final I18nResource i18nResource;
    private final LocaleHolder localeHolder;

    /**
     * 构造时指定业务组件
     *
     * @param userService 业务组件
     */
    public ApiUserController(UserService userService,
                             I18nResource i18nResource,
                             LocaleHolder localeHolder) {
        super(userService);
        this.userService = userService;
        this.i18nResource = i18nResource;
        this.localeHolder = localeHolder;
    }

    /**
     * 用户登录
     *
     * @param param 参数
     * @return GR
     */
    @PostMapping("/login")
    public GeneralResult<AccessToken> login(@RequestBody UserLoginDto param) {
        checkLoginParam(param);
        final String localeName = param.getLocale();
        Locale locale;
        log.debug("Find locale [{}] from params!", localeName);
        try {
            locale = LocaleTable.getInstance().get(localeName);
        } catch (Exception e) {
            locale = LocaleTable.getInstance().getDefault();
        }
        localeHolder.setLocale(locale);
        AccessToken accessToken = userService.login4Token(param);
        return GeneralResult.ok(accessToken);
    }

    /**
     * 检查登录参数
     *
     * @param param 参数
     */
    private void checkLoginParam(UserLoginDto param) {
        final Class<BadRequestException> ex = BadRequestException.class;
        ParamChecker.notNull(param, ex, I18nResource.NO_PARAMS);
        ParamChecker.notEmpty(param.getLoginName(), ex, i18nResource.getText(I18nResource.VALIDATE_REQUIRE, localeHolder.getLocale(), "Login Name"));
        ParamChecker.notEmpty(param.getPassword(), ex, i18nResource.getText(I18nResource.VALIDATE_REQUIRE, localeHolder.getLocale(), "Password"));
    }

    /**
     * 通过email判断用户是否存在
     *
     * @param email email
     * @return 是否存在
     */
    @GetMapping("/exists/email")
    public boolean existsByEmail(@RequestParam("email") String email) {
        return userService.existsByEmail(email);
    }
}
