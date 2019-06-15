package com.monolithiot.iot.user.web.controller;

import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.general.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By leven ont 2019/6/15 23:31
 * Class Name :[UserController]
 * 用户相关数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public GeneralResult<User> detail(@PathVariable("id") Integer id) {
        User user = userService.get(id);
        return GeneralResult.ok(user);
    }
}
