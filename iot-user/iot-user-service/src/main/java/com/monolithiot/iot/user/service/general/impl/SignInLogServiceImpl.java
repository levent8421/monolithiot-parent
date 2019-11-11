package com.monolithiot.iot.user.service.general.impl;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.utils.DateTimeUtils;
import com.monolithiot.iot.service.basic.impl.AbstractServiceImpl;
import com.monolithiot.iot.user.entity.SignInLog;
import com.monolithiot.iot.user.repository.SignInLogMapper;
import com.monolithiot.iot.user.service.general.SignInLogService;
import com.monolithiot.iot.user.service.listener.SignInListener;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Create by 郭文梁 2019/11/11 18:14
 * SignInLogServiceImpl
 * SignIn Log Service Implementation
 *
 * @author 郭文梁
 * @data 2019/11/11 18:14
 */
@Slf4j
@Service
public class SignInLogServiceImpl extends AbstractServiceImpl<SignInLog> implements SignInLogService {
    private final SignInLogMapper signInLogMapper;

    public SignInLogServiceImpl(SignInLogMapper signInLogMapper) {
        super(signInLogMapper);
        this.signInLogMapper = signInLogMapper;
    }

    /**
     * Build A Log Object
     *
     * @param userId user Id
     * @return log Object
     * @date sign in  date
     */
    private SignInLog buildLog(int userId, Date date) {
        val logObj = new SignInLog();
        logObj.setSignInDate(date);
        logObj.setUserId(userId);
        return logObj;
    }

    @Override
    public SignInLog signIn(Integer userId, SignInListener signInListener) {
        val now = DateTimeUtils.now();
        var signInLog = findSignInLog(userId, now);
        if (signInLog != null) {
            throw new BadRequestException("今日已签到！");
        }
        signInLog = buildLog(userId, now);
        signInLog = save(signInLog);
        if (isContinuousSignIn(userId, now)) {
            log.info("User ContinuousSignIn [{}]!", userId);
            signInListener.onConsecutiveSignIn(userId);
        } else {
            log.info("User SignIn [{}]!", userId);
            signInListener.onSignIn(userId);
        }
        return signInLog;
    }

    /**
     * 判断本次签到是否为连续签到
     *
     * @param userId 用户ID
     * @param now    当前时间
     * @return 是否联系
     */
    private boolean isContinuousSignIn(int userId, Date now) {
        val yesterday = DateTimeUtils.incField(now, Calendar.DAY_OF_YEAR, -1);
        return findSignInLog(userId, yesterday) != null;
    }

    @Override
    public SignInLog findSignInLog(int userId, Date date) {
        return signInLogMapper.selectDateAndUser(date, userId);
    }

    @Override
    public List<SignInLog> findByUserAndMonth(Integer userId, int year, int month) {
        return signInLogMapper.selectByUserAndMonth(userId, year, month);
    }
}
