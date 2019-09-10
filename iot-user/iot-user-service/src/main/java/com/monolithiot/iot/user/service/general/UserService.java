package com.monolithiot.iot.user.service.general;

import com.monolithiot.iot.commons.dto.UserLoginDto;
import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.service.basic.AbstractService;
import com.monolithiot.iot.user.entity.User;
import com.monolithiot.iot.user.service.listener.UserRegisterListener;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create by 郭文梁 2019/6/15 0015 17:20
 * UserService
 * 用户业务行为定义
 *
 * @author 郭文梁
 * @data 2019/6/15 0015
 */
public interface UserService extends AbstractService<User> {
    /**
     * 用户登录 返回登录令牌
     *
     * @param param 参数
     * @return AccessToken
     */
    AccessToken login4Token(UserLoginDto param);

    /**
     * 用户注册
     *
     * @param user     用户 携带注册参数
     * @param listener 注册监听器
     * @return User Entity
     */
    User register(User user, UserRegisterListener listener);

    /**
     * 通过手机号注册
     *
     * @param user                用户
     * @param notificationTraceId 短信通知记录号
     * @param verificationCode    短信验证码
     * @return 注册结果
     */
    User registerWithPhone(User user, String notificationTraceId, String verificationCode);

    /**
     * 设置用户的头像
     *
     * @param user       用户
     * @param avatarFile 图片文件
     * @return 图片文件访问路径
     */
    String setAvatar(User user, MultipartFile avatarFile);

    /**
     * Update user phone by sms verification code
     *
     * @param user             user
     * @param smsTraceId       sms verification code trade id
     * @param verificationCode verification code
     * @return User
     */
    User updatePhone(User user, String smsTraceId, String verificationCode);

    /**
     * Bind user email by notification trace id
     *
     * @param traceId traceId
     */
    void bindEmailByTraceId(String traceId);
}
