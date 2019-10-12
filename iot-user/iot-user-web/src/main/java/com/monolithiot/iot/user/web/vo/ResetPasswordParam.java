package com.monolithiot.iot.user.web.vo;

import lombok.Data;

/**
 * Create by 郭文梁 2019/10/12 19:06
 * ResetPasswordParam
 * 重置密码参数
 *
 * @author 郭文梁
 * @data 2019/10/12 19:06
 */
@Data
public class ResetPasswordParam {
    private String tradeId;
    private String password;
}
