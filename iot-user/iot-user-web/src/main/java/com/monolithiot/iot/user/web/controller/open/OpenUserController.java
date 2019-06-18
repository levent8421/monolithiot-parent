package com.monolithiot.iot.user.web.controller.open;

import com.monolithiot.iot.user.service.general.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public OpenUserController(UserService userService) {
        this.userService = userService;
    }
}
