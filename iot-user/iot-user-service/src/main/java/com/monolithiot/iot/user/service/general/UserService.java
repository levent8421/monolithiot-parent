package com.monolithiot.iot.user.service.general;

import com.monolithiot.iot.commons.dto.UserLoginDto;
import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.service.basic.AbstractService;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.general.listener.UserRegisterListener;

/**
 * Create by 郭文梁 2019/6/15 0015 17:20
 * UserService
 * 用户业务行为定义
 *
 * @author 郭文梁
 * @data 2019/6/15 0015
 */
public interface UserService extends AbstractService<User> {
    /**
     * 用户登录 返回登录令牌
     *
     * @param param 参数
     * @return AccessToken
     */
    AccessToken login4Token(UserLoginDto param);

    /**
     * 用户注册
     *
     * @param user     用户 携带注册参数
     * @param listener 注册监听器
     * @return User Entity
     */
    User register(User user, UserRegisterListener listener);
}
