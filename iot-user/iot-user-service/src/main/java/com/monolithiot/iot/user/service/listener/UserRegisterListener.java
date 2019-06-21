package com.monolithiot.iot.user.service.listener;

import com.monolithiot.iot.user.entity.User;

/**
 * Create By leven ont 2019/6/18 22:36
 * Class Name :[UserRegisterListener]
 * <p>
 * 监听用户注册行为
 *
 * @author leven
 */
public interface UserRegisterListener {
    /**
     * 用户注册成功后调用
     *
     * @param user 用户
     */
    void onRegister(User user);
}
