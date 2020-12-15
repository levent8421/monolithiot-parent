package com.monolithiot.iot.user.web.controller.open;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.resource.I18nResource;
import com.monolithiot.iot.resource.locale.LocaleHolder;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.general.UserService;
import com.monolithiot.iot.user.service.listener.UserRegisterListener;
import com.monolithiot.iot.user.web.vo.ResetPasswordParam;
import com.monolithiot.iot.user.web.vo.UserRegisterParam;
import com.monolithiot.iot.web.controller.AbstractController;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.monolithiot.iot.commons.utils.ParamChecker.notEmpty;
import static com.monolithiot.iot.commons.utils.ParamChecker.notNull;
import static com.monolithiot.iot.commons.utils.TextUtils.isTrimedEmpty;

/**
 * Create By leven ont 2019/6/17 23:52
 * Class Name :[OpenUserController]
 * <p>
 * 开放权限的用户数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/open/user")
public class OpenUserController extends AbstractController {
    private final UserService userService;
    private final UserRegisterListener userRegisterListener;
    private final LocaleHolder localeHolder;
    private final I18nResource i18nResource;

    public OpenUserController(UserService userService,
                              UserRegisterListener userRegisterListener,
                              LocaleHolder localeHolder,
                              I18nResource i18nResource) {
        this.userService = userService;
        this.userRegisterListener = userRegisterListener;
        this.localeHolder = localeHolder;
        this.i18nResource = i18nResource;
    }

    /**
     * 用户注册
     *
     * @param param 注册参数
     * @return GR with User Entity
     */
    @PostMapping("/register")
    public GeneralResult<User> register(@RequestBody UserRegisterParam param) {
        User user = new User();
        checkAndCopyRegisterParam(param, user);
        User res = userService.register(user, userRegisterListener);
        return GeneralResult.ok(res);
    }

    /**
     * 检查并拷贝注册参数
     *
     * @param param 参数
     * @param user  拷贝目标
     */
    private void checkAndCopyRegisterParam(UserRegisterParam param, User user) {
        Class<BadRequestException> ex = BadRequestException.class;
        notNull(param, ex, getResource(I18nResource.NO_PARAMS));
        notEmpty(param.getName(), ex, getResource(I18nResource.VALIDATE_REQUIRE, "username"));
        notEmpty(param.getPassword(), ex, getResource(I18nResource.VALIDATE_REQUIRE, "password"));
        if (isTrimedEmpty(param.getEmail()) && isTrimedEmpty(param.getPhone())) {
            throw new BadRequestException(getResource(I18nResource.VALIDATE_REQUIRE, "phone or email"));
        }
        user.setName(param.getName());
        user.setPassword(param.getPassword());
        user.setEmail(param.getEmail());
        user.setPhone(param.getPhone());
    }

    /**
     * 通过手机号注册
     *
     * @param param 参数
     * @return GR
     */
    @PutMapping("/register-with-phone")
    public GeneralResult<User> registerWithPhone(@RequestBody UserRegisterParam param) {
        final User user = new User();
        checkAndCopyRegisterWithPhoneParam(param, user);
        User res = userService.registerWithPhone(user, param.getNotificationTraceId(), param.getVerificationCode());
        return GeneralResult.ok(res);
    }

    /**
     * 检查并拷贝注册参数（通过电话号注册）
     *
     * @param param  参数
     * @param target 拷贝目标
     */
    private void checkAndCopyRegisterWithPhoneParam(UserRegisterParam param, User target) {
        final Class<BadRequestException> ex = BadRequestException.class;
        notNull(param, ex, getResource(I18nResource.NO_PARAMS));
        notEmpty(param.getName(), ex, getResource(I18nResource.VALIDATE_REQUIRE, "username"));
        notEmpty(param.getPassword(), ex, getResource(I18nResource.VALIDATE_REQUIRE, "password"));
        notEmpty(param.getPhone(), ex, getResource(I18nResource.VALIDATE_REQUIRE, "phone"));
        notEmpty(param.getVerificationCode(), ex, getResource(I18nResource.VALIDATE_REQUIRE, "smsVerifyCode"));
        notEmpty(param.getNotificationTraceId(), ex, getResource(I18nResource.VALIDATE_REQUIRE, "traceId"));
        target.setName(param.getName());
        target.setPassword(param.getPassword());
        target.setPhone(param.getPhone());
    }

    /**
     * Bind email by email trace id
     *
     * @param traceId email trace Id
     * @return ModelAndView
     */
    @GetMapping("/bind-email/{emailTraceId}")
    public ModelAndView bindEmail(@PathVariable("emailTraceId") String traceId) {
        Map<String, Object> model = new HashMap<>(16);
        try {
            userService.bindEmailByTraceId(traceId);
            model.put("msg", "绑定成功");
        } catch (Exception e) {
            model.put("msg", "绑定失败，" + e.getMessage());
        }
        return new ModelAndView("bind-email-result", model);
    }

    /**
     * 进入重置密码页面
     *
     * @param emailTradeId 邮箱tradeId
     * @return MV
     */
    @GetMapping("/reset-password/{emailTradeId}")
    public ModelAndView resetPassword(@PathVariable("emailTradeId") String emailTradeId) {
        val viewName = "reset-password";
        val model = new HashMap<String, Object>(16);
        model.put("traceId", emailTradeId);
        return new ModelAndView(viewName, model);
    }

    /**
     * Reset password
     *
     * @param params params
     * @return GR
     */
    @PostMapping("/reset-password-by-trade-id")
    public GeneralResult<Void> resetPasswordByEmailTradeId(@RequestBody ResetPasswordParam params) {
        checkResetPasswordParam(params);
        userService.resetPasswordByEmailTradeId(params.getTradeId(), params.getPassword());
        return GeneralResult.ok();
    }

    /**
     * Check reset password param
     *
     * @param param param object
     */
    private void checkResetPasswordParam(ResetPasswordParam param) {
        val ex = BadRequestException.class;
        notNull(param, ex, getResource(I18nResource.NO_PARAMS));
        notEmpty(param.getTradeId(), ex, getResource(I18nResource.VALIDATE_REQUIRE, "traceId"));
        notEmpty(param.getPassword(), ex, getResource(I18nResource.VALIDATE_REQUIRE, "password"));
    }

    private String getResource(String id, Object... args) {
        final Locale locale = localeHolder.getLocale();
        return i18nResource.getText(id, locale, args);
    }
}
