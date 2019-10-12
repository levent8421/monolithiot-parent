package com.monolithiot.iot.user.service.general.impl;

import com.monolithiot.iot.commons.dto.EmailDto;
import com.monolithiot.iot.commons.dto.UserLoginDto;
import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import com.monolithiot.iot.commons.prop.PathProp;
import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.commons.utils.MultipartFileUtil;
import com.monolithiot.iot.commons.utils.ParamChecker;
import com.monolithiot.iot.commons.utils.TextUtils;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.commons.vo.notification.VerifySmsCodeParam;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.monolithiot.iot.service.encrypt.PasswordEncryptor;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.repository.UserMapper;
import com.monolithiot.iot.user.service.feign.NotificationFeignClient;
import com.monolithiot.iot.user.service.general.UserService;
import com.monolithiot.iot.user.service.listener.UserRegisterListener;
import com.monolithiot.iot.user.token.UserAccessToken;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
@Slf4j
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {
    /**
     * 默认令牌有效期 20周
     */
    private static final long DEFAULT_ACCESS_TOKEN_EXPIRE_IN = 20L * 7L * 24L * 60L * 60L * 1000L;
    private final UserMapper userMapper;
    private final PasswordEncryptor passwordEncryptor;
    private final NotificationFeignClient notificationFeignClient;
    private final PathProp pathProp;

    public UserServiceImpl(UserMapper mapper,
                           PasswordEncryptor passwordEncryptor,
                           NotificationFeignClient notificationFeignClient,
                           PathProp pathProp) {
        super(mapper);
        this.userMapper = mapper;
        this.passwordEncryptor = passwordEncryptor;
        this.notificationFeignClient = notificationFeignClient;
        this.pathProp = pathProp;
    }

    @Override
    public <T extends User> void resolvePath(T e) {
        if (e == null) {
            return;
        }
        val server = pathProp.getStaticServerPrefix();
        if (TextUtils.isTrimedNotEmpty(e.getAvatar())) {
            e.setAvatar(server + pathProp.getUserAvatarPath() + e.getAvatar());
        }
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
        final GeneralResult<Boolean> res = notificationFeignClient.verifySmsCode(param);
        if (res.getCode() != HttpStatus.OK.value()) {
            throw new InternalServerErrorException(res.getMsg());
        }
        return res.getData();
    }

    @Override
    public String setAvatar(User user, MultipartFile avatarFile) {
        val path = pathProp.getStaticFilePath() + pathProp.getUserAvatarPath();
        try {
            val filename = MultipartFileUtil.save(avatarFile, path);
            user.setAvatar(filename);
            updateById(user);
        } catch (IOException e) {
            throw new InternalServerErrorException("保存头像文件失败！", e);
        }
        resolvePath(user);
        return user.getAvatar();
    }

    @Override
    public User updatePhone(User user, String smsTraceId, String verificationCode) {
        VerifySmsCodeParam param = new VerifySmsCodeParam();
        param.setTraceNo(smsTraceId);
        param.setVerificationCode(verificationCode);
        val res = notificationFeignClient.verifySmsCodeAndGetPhoneNumber(param);
        checkResult(res);
        val phone = res.getData();
        user.setPhone(phone);
        return updateById(user);
    }

    /**
     * 检查返回数据
     *
     * @param res 返回数据
     */
    private void checkResult(GeneralResult<?> res) {
        if (res.getCode() != HttpStatus.OK.value()) {
            throw new InternalServerErrorException(res.getMsg());
        }
    }

    @Override
    public void bindEmailByTraceId(String traceId) {
        val email = notificationFeignClient.findEmailByTraceId(traceId);
        ParamChecker.notNull(email.getUserId(), BadRequestException.class, "该链接无效！");
        if (!Objects.equals(email.getIntention(), EmailDto.INTENTION_UPDATE_EMAIL)) {
            throw new BadRequestException("该邮件不能作为更新邮箱地址验证邮件使用！");
        }
        val user = require(email.getUserId());
        user.setEmail(email.getTarget());
        updateById(user);
        log.info("Update user`s [{}] email to [{}]!", user.getId(), email.getTarget());
    }

    @Override
    public boolean existsByEmail(String email) {
        val query = new User();
        query.setEmail(email);
        return exists(query);
    }

    @Override
    public void resetPasswordByEmailTradeId(String tradeId, String password) {
        val email = notificationFeignClient.findEmailByTraceId(tradeId);
        if (email == null) {
            throw new BadRequestException("链接无效或已过期！");
        }
        val eamil = email.getTarget();
        val user = findByEmail(eamil);
        if (user == null) {
            throw new BadRequestException("该邮箱已失效！");
        }
        user.setPassword(passwordEncryptor.encode(password));
        updateById(user);
    }

    /**
     * Find User by email
     *
     * @param email email
     * @return user
     */
    private User findByEmail(String email) {
        val query = new User();
        query.setEmail(email);
        return findOneByQuery(query);
    }
}
