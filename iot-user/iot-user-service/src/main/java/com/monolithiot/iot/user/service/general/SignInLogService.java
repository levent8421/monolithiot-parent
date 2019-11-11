package com.monolithiot.iot.user.service.general;

import com.monolithiot.iot.service.basic.AbstractService;
import com.monolithiot.iot.user.entity.SignInLog;
import com.monolithiot.iot.user.service.listener.SignInListener;

import java.util.Date;
import java.util.List;

/**
 * Create by 郭文梁 2019/11/11 18:13
 * SignInLogService
 * SignInLog Service
 *
 * @author 郭文梁
 * @data 2019/11/11 18:13
 */
public interface SignInLogService extends AbstractService<SignInLog> {
    /**
     * 签到
     *
     * @param userId         用户ID
     * @param signInListener 签到监听器
     * @return SignInLog Object
     */
    SignInLog signIn(Integer userId, SignInListener signInListener);

    /**
     * 查询签到记录
     *
     * @param userId 用户ID
     * @param date   日期
     * @return SignInLog Object
     */
    SignInLog findSignInLog(int userId, Date date);

    /**
     * find log list by user id and month
     *
     * @param userId userId
     * @param year   year
     * @param month  month
     * @return Log List
     */
    List<SignInLog> findByUserAndMonth(Integer userId, int year, int month);
}
