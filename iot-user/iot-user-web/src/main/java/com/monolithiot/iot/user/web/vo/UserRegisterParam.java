package com.monolithiot.iot.user.web.vo;

import lombok.Data;

/**
 * Create By leven ont 2019/6/18 22:32
 * Class Name :[UserRegisterParam]
 * <p>
 * 注册用户需要的参数
 *
 * @author leven
 */
@Data
public class UserRegisterParam {
    /**
     * 用户名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 短信验证码
     */
    private String verificationCode;
    /**
     * 短信通知记录号
     */
    private String notificationTraceId;
}
