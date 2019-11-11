package com.monolithiot.iot.user.service.listener.impl;

import com.monolithiot.iot.user.service.general.UserService;
import com.monolithiot.iot.user.service.listener.SignInListener;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 郭文梁 2019/11/11 19:57
 * SignInListenerImpl
 * SignIn Listener Implementation
 *
 * @author 郭文梁
 * @data 2019/11/11 19:57
 */
@Component
@Slf4j
public class SignInListenerImpl implements SignInListener {
    /**
     * 积分表
     */
    private static final Map<Integer, Integer> SCORE_TABLE;
    /**
     * 最大签到连续签到数量
     */
    private static final int MAX_CONSECUTIVE_SIGN_IN_COUNT = 7;
    /**
     * 最多送积分数量
     */
    public static final int MAX_SCORE = 7;

    static {
        SCORE_TABLE = new HashMap<>();
        initScoreTable();
    }

    private final UserService userService;

    public SignInListenerImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * Init Score Table
     */
    private static void initScoreTable() {
        SCORE_TABLE.put(1, 1);
        SCORE_TABLE.put(2, 2);
        SCORE_TABLE.put(3, 3);
        SCORE_TABLE.put(4, 4);
        SCORE_TABLE.put(5, 5);
        SCORE_TABLE.put(6, 6);
        SCORE_TABLE.put(7, 7);
    }

    @Override
    public void onConsecutiveSignIn(int userId) {
        val user = userService.require(userId);
        val consecutiveSignInCount = user.getConsecutiveSignInCount();
        int score = 0;
        if (consecutiveSignInCount >= MAX_CONSECUTIVE_SIGN_IN_COUNT) {
            score = MAX_SCORE;
        }
    }

    @Override
    public void onSignIn(int userId) {

    }
}
