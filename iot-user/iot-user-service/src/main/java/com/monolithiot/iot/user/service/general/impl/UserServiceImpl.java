package com.monolithiot.iot.user.service.general.impl;

import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.repository.UserMapper;
import com.monolithiot.iot.user.service.general.UserService;
import org.springframework.stereotype.Service;

/**
 * Create by 郭文梁 2019/6/15 0015 17:21
 * UserServiceImpl
 * 用户业务行为实现
 *
 * @author 郭文梁
 * @data 2019/6/15 0015
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper mapper) {
        super(mapper);
        this.userMapper = mapper;
    }
}
