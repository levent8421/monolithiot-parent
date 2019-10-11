package com.monolithiot.iot.notification.feign;

import com.monolithiot.iot.commons.context.ApplicationConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create by 郭文梁 2019/10/11 18:56
 * UserFeignClient
 * User Feign client
 *
 * @author 郭文梁
 * @data 2019/10/11 18:56
 */
@Component
@FeignClient(ApplicationConstants.Service.USER)
@RequestMapping("/api")
public interface UserFeignClient {
    /**
     * Exists by email
     *
     * @param email email
     * @return exists?
     */
    @GetMapping("/user/exists/email")
    boolean existsByEmail(@RequestParam("email") String email);
}
