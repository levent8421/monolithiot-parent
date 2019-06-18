package com.monolithiot.iot.user.service.general.impl;

import com.monolithiot.iot.commons.dto.UserLoginDto;
import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.monolithiot.iot.service.encrypt.PasswordEncryptor;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.repository.UserMapper;
import com.monolithiot.iot.user.service.general.UserService;
import com.monolithiot.iot.user.service.general.listener.UserRegisterListener;
import com.monolithiot.iot.user.token.UserAccessToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    /**
     * 默认令牌有效期 20周
     */
    private static final long DEFAULT_ACCESS_TOKEN_EXPIRE_IN = 20L * 7L * 24L * 60L * 60L * 1000L;
    private final UserMapper userMapper;
    private final PasswordEncryptor passwordEncryptor;

    public UserServiceImpl(UserMapper mapper,
                           PasswordEncryptor passwordEncryptor) {
        super(mapper);
        this.userMapper = mapper;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public AccessToken login4Token(UserLoginDto param) {
        User user = userMapper.selectByLoginName(param.getLoginName());
        if (user == null) {
            throw new BadRequestException("用户名或密码错误");
        }
        checkUserPassword(user, param.getPassword());
        return UserAccessToken.fromUser(user, DEFAULT_ACCESS_TOKEN_EXPIRE_IN);
    }

    @Override
    public User register(User user, UserRegisterListener listener) {
        checkUserExists(user);
        String password = passwordEncryptor.encode(user.getPassword());
        user.setPassword(password);
        user.setGender(User.GENDER_UNKNOWN);
        final User saveRes = save(user);
        listener.onRegister(saveRes);
        return saveRes;
    }

    /**
     * 检查用户登录密码
     *
     * @param user     用户
     * @param password 密码
     */
    private void checkUserPassword(User user, String password) {
        if (!passwordEncryptor.matches(user.getPassword(), password)) {
            throw new BadRequestException("用户名或密码错误");
        }
    }

    /**
     * 检查用户是否已经被注册
     *
     * @param user 用户
     */
    private void checkUserExists(User user) {
        User existsUser = userMapper.selectByNameOrPhoneOrEmail(user.getName(), user.getPhone(), user.getEmail());
        if (existsUser != null) {
            if (Objects.equals(existsUser.getName(), user.getName())) {
                throw new BadRequestException("用户名已被注册!");
            }
            if (Objects.equals(existsUser.getPhone(), user.getPhone())) {
                throw new BadRequestException("电话号已被注册!");
            }
            if (Objects.equals(existsUser.getEmail(), user.getEmail())) {
                throw new BadRequestException("邮箱已被注册!");
            }
        }
    }
}
