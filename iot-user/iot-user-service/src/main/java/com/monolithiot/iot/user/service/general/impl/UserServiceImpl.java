package com.monolithiot.iot.user.service.general.impl;

import com.monolithiot.iot.commons.dto.UserLoginDto;
import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.commons.utils.TextUtils;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.commons.vo.notification.VerifySmsCodeParam;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.monolithiot.iot.service.encrypt.PasswordEncryptor;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.repository.UserMapper;
import com.monolithiot.iot.user.service.feign.SmsVerificationCodeFeignClient;
import com.monolithiot.iot.user.service.general.UserService;
import com.monolithiot.iot.user.service.listener.UserRegisterListener;
import com.monolithiot.iot.user.token.UserAccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final SmsVerificationCodeFeignClient smsVerificationCodeFeignClient;

    public UserServiceImpl(UserMapper mapper,
                           PasswordEncryptor passwordEncryptor,
                           SmsVerificationCodeFeignClient smsVerificationCodeFeignClient) {
        super(mapper);
        this.userMapper = mapper;
        this.passwordEncryptor = passwordEncryptor;
        this.smsVerificationCodeFeignClient = smsVerificationCodeFeignClient;
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
            if (TextUtils.compareIgnoreCase(existsUser.getName(), user.getName())) {
                throw new BadRequestException("用户名已被注册!");
            }
            if (TextUtils.compareIgnoreCase(existsUser.getPhone(), user.getPhone())) {
                throw new BadRequestException("电话号已被注册!");
            }
            if (TextUtils.compareIgnoreCase(existsUser.getEmail(), user.getEmail())) {
                throw new BadRequestException("邮箱已被注册!");
            }
        }
    }

    @Override
    public User registerWithPhone(User user, String notificationTraceId, String verificationCode) {
        checkUserExists(user);
        if (!checkSmsVerifyCode(verificationCode, notificationTraceId)) {
            throw new BadRequestException("验证码错误");
        }
        user.setPassword(passwordEncryptor.encode(user.getPassword()));
        user.setGender(User.GENDER_UNKNOWN);
        return save(user);
    }

    /**
     * 检查短信验证码
     *
     * @param code    验证码
     * @param traceNo 记录号
     * @return 是否匹配
     */
    private boolean checkSmsVerifyCode(String code, String traceNo) {
        VerifySmsCodeParam param = new VerifySmsCodeParam();
        param.setTraceNo(traceNo);
        param.setVerificationCode(code);
        final GeneralResult<Boolean> res = smsVerificationCodeFeignClient.verifySmsCode(param);
        if (res.getCode() != HttpStatus.OK.value()) {
            throw new InternalServerErrorException(res.getMsg());
        }
        return res.getData();
    }

    @Override
    public String setAvatar(User user, MultipartFile avatarFile) {
        
        return null;
    }
}
