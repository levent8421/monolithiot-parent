package com.monolithiot.iot.user.web.controller.safe;

import com.monolithiot.iot.commons.utils.HttpRequestUtils;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.general.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * Create By leven ont 2019/6/15 23:31
 * Class Name :[UserController]
 * 用户相关数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取用户详细信息
     *
     * @param id      ID
     * @param request 请求
     * @return GR
     */
    @GetMapping("/{id}")
    public GeneralResult<User> detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        log.debug("User Id [{}]", HttpRequestUtils.obtainUserIdFromtRequest(request));
        log.debug("User Login Name [{}]", HttpRequestUtils.obtainLoginNameFromRequest(request));
        User user = userService.get(id);
        return GeneralResult.ok(user);
    }
    /**
     * 获取当前登录的用户
     *
     * @param request 请求对象
     * @return GR
     */
    @GetMapping("/me")
    public GeneralResult<User> me(HttpServletRequest request) {
        final Integer userId = HttpRequestUtils.obtainUserIdFromtRequest(request);
        @NotNull final User user = userService.require(userId);
        return GeneralResult.ok(user);
    }
}
