package com.monolithiot.iot.user.service.listener.impl;

import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.listener.UserRegisterListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Create By leven ont 2019/6/18 22:37
 * Class Name :[UserRegisterListenerImpl]
 * <p>
 * 用户注册监听器实现
 *
 * @author leven
 */
@Component
@Slf4j
public class UserRegisterListenerImpl implements UserRegisterListener {
    @Override
    public void onRegister(User user) {
    }
}
