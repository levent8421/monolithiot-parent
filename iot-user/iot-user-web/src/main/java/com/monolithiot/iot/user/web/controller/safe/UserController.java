package com.monolithiot.iot.user.web.controller.safe;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.utils.HttpRequestUtils;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.general.UserService;
import com.monolithiot.iot.user.web.vo.UpdatePhoneParam;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import static com.monolithiot.iot.commons.utils.ParamChecker.notEmpty;

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
public class UserController extends AbstractEntityController<User> {
    private final UserService userService;

    public UserController(UserService userService) {
        super(userService);
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

    /**
     * 更新用户信息
     *
     * @param request 请求对象
     * @param param   参数
     * @return GR with user entity
     */
    @PostMapping("/update")
    public GeneralResult<User> updateUserInfo(HttpServletRequest request,
                                              @RequestBody User param) {
        val userId = HttpRequestUtils.obtainUserIdFromtRequest(request);
        val user = userService.require(userId);
        copyUpdateParam(param, user);
        val res = userService.updateById(user);
        return GeneralResult.ok(res);
    }

    /**
     * 拷贝更新用户信息的参数
     *
     * @param param  参数
     * @param target 拷贝地址
     */
    private void copyUpdateParam(User param, User target) {
        target.setGender(param.getGender());
        target.setProvince(param.getProvince());
        target.setCity(param.getCity());
        target.setDistrict(param.getDistrict());
        target.setAddress(param.getAddress());
        target.setIndustry(param.getIndustry());
    }

    /**
     * 设置用户的头像
     *
     * @param avatarFile 头像图片文件
     * @param request    请求对象
     * @return GR
     */
    @PostMapping("/avatar")
    public GeneralResult<String> setAvatar(MultipartFile avatarFile, HttpServletRequest request) {
        notEmpty(avatarFile, BadRequestException.class, "头像文件未上传！");
        val userId = HttpRequestUtils.obtainUserIdFromtRequest(request);
        @NotNull User user = userService.require(userId);
        val avatarUrl = userService.setAvatar(user, avatarFile);
        return GeneralResult.ok(avatarUrl);
    }

    /**
     * 更新电话号
     *
     * @param param   params
     * @param request Request
     * @return GR
     */
    @PostMapping("/phone")
    public GeneralResult<User> setPhone(@RequestBody UpdatePhoneParam param,
                                        HttpServletRequest request) {
        val userId = requireCurrentUserId(request);
        val user = userService.require(userId);
        val res = userService.updatePhone(user, param.getSmsTraceId(), param.getVerificationCode());
        return GeneralResult.ok(res);
    }
}
