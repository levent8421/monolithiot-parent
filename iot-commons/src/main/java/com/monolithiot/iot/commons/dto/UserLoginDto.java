package com.monolithiot.iot.commons.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * Create by 郭文梁 2019/6/18 0018 14:33
 * UserLoginDto
 * 用户登录参数
 *
 * @author 郭文梁
 * @data 2019/6/18 0018
 */
@Data
public class UserLoginDto {
    /**
     * 从JSON字符串创建参数对象
     *
     * @param jsonStr json
     * @return 参数对象
     */
    public static UserLoginDto fromJson(String jsonStr) {
        return JSON.parseObject(jsonStr, UserLoginDto.class);
    }

    /**
     * 用户登录名
     */
    private String loginName;
    /**
     * 登录密码
     */
    private String password;
}
