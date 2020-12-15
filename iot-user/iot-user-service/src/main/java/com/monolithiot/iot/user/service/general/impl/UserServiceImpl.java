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
import com.monolithiot.iot.resource.I18nResource;
import com.monolithiot.iot.resource.locale.LocaleHolder;
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
import java.util.Locale;
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
    private final I18nResource i18nResource;
    private final LocaleHolder localeHolder;

    public UserServiceImpl(UserMapper mapper,
                           PasswordEncryptor passwordEncryptor,
                           NotificationFeignClient notificationFeignClient,
                           PathProp pathProp,
                           I18nResource i18nResource,
                           LocaleHolder localeHolder) {
        super(mapper);
        this.userMapper = mapper;
        this.passwordEncryptor = passwordEncryptor;
        this.notificationFeignClient = notificationFeignClient;
        this.pathProp = pathProp;
        this.i18nResource = i18nResource;
        this.localeHolder = localeHolder;
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
            throw new BadRequestException(getResource(I18nResource.PASSWORD_ERROR));
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
            throw new BadRequestException(getResource(I18nResource.PASSWORD_ERROR));
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
                throw new BadRequestException(getResource(I18nResource.DUPLICATE_NAME));
            }
            if (TextUtils.compareIgnoreCase(existsUser.getPhone(), user.getPhone())) {
                throw new BadRequestException(getResource(I18nResource.DUPLICATE_PHONE));
            }
            if (TextUtils.compareIgnoreCase(existsUser.getEmail(), user.getEmail())) {
                throw new BadRequestException(getResource(I18nResource.DUPLICATE_EMAIL));
            }
        }
    }

    @Override
    public User registerWithPhone(User user, String notificationTraceId, String verificationCode) {
        checkUserExists(user);
        if (!checkSmsVerifyCode(verificationCode, notificationTraceId)) {
            throw new BadRequestException(getResource(I18nResource.BAD_VERIFY_CODE));
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
            throw new InternalServerErrorException(getResource(I18nResource.ERROR_ON_SAVE_AVATAR), e);
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
        ParamChecker.notNull(email.getUserId(), BadRequestException.class, i18nResource.getText(I18nResource.INVALIDATE_LINK));
        if (!Objects.equals(email.getIntention(), EmailDto.INTENTION_UPDATE_EMAIL)) {
            throw new BadRequestException(getResource(I18nResource.INVALIDATE_LINK));
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
            throw new BadRequestException(getResource(I18nResource.INVALIDATE_LINK));
        }
        val eamil = email.getTarget();
        val user = findByEmail(eamil);
        if (user == null) {
            throw new BadRequestException(getResource(I18nResource.INVALIDATE_EMAIL));
        }
        user.setPassword(getResource(password));
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

    @Override
    public void incConsecutiveSignInCount(Integer userId, int amount) {
        val resRow = userMapper.incConsecutiveSignInCount(userId, amount);
        log.info("Inc ConsecutiveSignInCount userId=[{}], amount=[{}], rows=[{}]", userId, amount, resRow);
    }

    @Override
    public void resetConsecutiveSignInCount(int userId, int value) {
        final int resRow = userMapper.updateConsecutiveSignInCountById(userId, value);
        log.info("Reset User[{}] consecutiveSignInCount [{}], resRow=[{}]!", userId, value, resRow);
    }

    @Override
    public void incPointScore(int userId, int score) {
        final int resRow = userMapper.incPointScore(userId, score);
        log.info("Inc user[{}], pointScore[{}], resRows = [{}]", userId, score, resRow);
    }

    private String getResource(String id, Object... args) {
        final Locale locale = localeHolder.getLocale();
        return i18nResource.getText(id, locale, args);
    }
}
