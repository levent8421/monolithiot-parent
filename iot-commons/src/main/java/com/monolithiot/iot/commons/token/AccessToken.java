package com.monolithiot.iot.commons.token;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * Create by 郭文梁 2019/6/18 0018 14:54
 * AccessToken
 * 访问令牌基类
 *
 * @author 郭文梁
 * @data 2019/6/18 0018
 */
@Data
public class AccessToken {
    /**
     * 角色：用户
     */
    public static final int ROLE_USER = 0x01;
    /**
     * 角色：匿名
     */
    public static final int ROLE_ANONYMOUS = 0x02;
    private String loginName;
    private Long createTime;
    private Long expireIn;
    private Integer userId;
    private Integer role;

    /**
     * 创建令牌
     *
     * @param loginName 登录名
     * @param expireIn  过期时间
     * @param userId    用户ID
     */
    public AccessToken(String loginName, Long expireIn, Integer userId) {
        this.loginName = loginName;
        this.expireIn = expireIn;
        this.userId = userId;
        this.createTime = System.currentTimeMillis();
        this.role = ROLE_USER;
    }

    /**
     * 转化为JSON字符串
     *
     * @return JSONString
     */
    public String asJson() {
        return JSON.toJSONString(this);
    }
}
