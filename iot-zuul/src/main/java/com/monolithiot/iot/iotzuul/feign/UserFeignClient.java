package com.monolithiot.iot.iotzuul.feign;

import com.monolithiot.iot.commons.context.ApplicationConstants;
import com.monolithiot.iot.commons.dto.UserLoginDto;
import com.monolithiot.iot.commons.token.AccessToken;
import com.monolithiot.iot.commons.vo.GeneralResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by 郭文梁 2019/6/18 0018 17:18
 * UserFeignClient
 * 用户相关FeignClient
 *
 * @author 郭文梁
 * @data 2019/6/18 0018
 */
@FeignClient(ApplicationConstants.Service.USER)
@RequestMapping("/api")
public interface UserFeignClient {
    /**
     * 用户登录
     *
     * @param param 登录参数
     * @return 令牌
     */
    @PostMapping("/user/login")
    GeneralResult<AccessToken> login(@RequestBody UserLoginDto param);
}
