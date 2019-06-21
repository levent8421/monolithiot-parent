package com.monolithiot.iot.user.service.feign;

import com.monolithiot.iot.commons.context.ApplicationConstants;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.commons.vo.notification.VerifySmsCodeParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create By leven ont 2019/6/21 21:48
 * Class Name :[SmsVerificationCodeFeignClient]
 * <p>
 * 短信验证码Feign客户端
 *
 * @author leven
 */
@FeignClient(ApplicationConstants.Service.NOTIFICATION)
@RequestMapping("/api")
public interface SmsVerificationCodeFeignClient {
    /**
     * 检验短信验证码
     *
     * @param param 参数
     * @return GR
     */
    @PostMapping("/sms-verification-code/verify")
    GeneralResult<Boolean> verifySmsCode(@RequestBody VerifySmsCodeParam param);
}
