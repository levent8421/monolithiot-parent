package com.monolithiot.iot.user.service.listener;

/**
 * Create by 郭文梁 2019/11/11 19:47
 * SignInListener
 * 签到监听器
 *
 * @author 郭文梁
 * @data 2019/11/11 19:47
 */
public interface SignInListener {
    /**
     * 连续签到时调用
     *
     * @param userId 用户ID
     */
    void onConsecutiveSignIn(int userId);

    /**
     * 断签后签到时调用
     *
     * @param userId userId
     */
    void onSignIn(int userId);
}
