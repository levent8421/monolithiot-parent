package com.monolithiot.iot.user.token;

import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.user.entity.User;

/**
 * Create by 郭文梁 2019/6/18 0018 14:45
 * UserAccessToken
 * 用户访问令牌
 *
 * @author 郭文梁
 * @data 2019/6/18 0018
 */
public class UserAccessToken extends AccessToken {

    /**
     * 创建用户令牌
     *
     * @param loginName 登录名
     * @param expireIn  过期时间
     * @param userId    用户ID
     */
    private UserAccessToken(String loginName, Long expireIn, Integer userId) {
        super(loginName, expireIn, userId);
    }

    /**
     * 从用户创建令牌
     *
     * @param user     用户
     * @param expireIn 令牌有效时间
     * @return AccessToken
     */
    public static UserAccessToken fromUser(User user, Long expireIn) {
        if (user == null) {
            throw new IllegalArgumentException("Could not create access token from a null user!");
        }
        return new UserAccessToken(user.getName(), expireIn, user.getId());
    }
}
