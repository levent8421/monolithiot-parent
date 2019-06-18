package com.monolithiot.iot.user.web.controller.api;

import com.monolithiot.iot.commons.dto.UserLoginDto;
import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.commons.utils.ParamChecker;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.general.UserService;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 郭文梁 2019/6/18 0018 18:05
 * ApiUserController
 * 用户Api相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/6/18 0018
 */
@RestController
@RequestMapping("/api/user")
public class ApiUserController extends AbstractEntityController<User> {
    private final UserService userService;

    /**
     * 构造时指定业务组件
     *
     * @param userService 业务组件
     */
    public ApiUserController(UserService userService) {
        super(userService);
        this.userService = userService;
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
        ParamChecker.notNull(param, ex, "参数未传");
        ParamChecker.notEmpty(param.getLoginName(), ex, "登录名必填");
        ParamChecker.notEmpty(param.getPassword(), ex, "密码必填");
    }
}
