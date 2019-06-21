package com.monolithiot.iot.user.web.controller.open;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.general.UserService;
import com.monolithiot.iot.user.service.listener.UserRegisterListener;
import com.monolithiot.iot.user.web.vo.UserRegisterParam;
import org.springframework.web.bind.annotation.*;

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
public class OpenUserController {
    private final UserService userService;
    private final UserRegisterListener userRegisterListener;

    public OpenUserController(UserService userService,
                              UserRegisterListener userRegisterListener) {
        this.userService = userService;
        this.userRegisterListener = userRegisterListener;
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
        notNull(param, ex, "参数未传！");
        notEmpty(param.getName(), ex, "用户名必填！");
        notEmpty(param.getPassword(), ex, "密码必填！");
        if (isTrimedEmpty(param.getEmail()) && isTrimedEmpty(param.getPhone())) {
            throw new BadRequestException("请填写邮箱或电话其中一个！");
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
        notNull(param, ex, "参数未传");
        notEmpty(param.getName(), ex, "用户名必填");
        notEmpty(param.getPassword(), ex, "密码必填");
        notEmpty(param.getPhone(), ex, "电话号必填");
        notEmpty(param.getVerificationCode(), ex, "短信验证码必填");
        notEmpty(param.getNotificationTraceId(), ex, "短信通知记录号必填");
        target.setName(param.getName());
        target.setPassword(param.getPassword());
        target.setPhone(param.getPhone());
    }
}
