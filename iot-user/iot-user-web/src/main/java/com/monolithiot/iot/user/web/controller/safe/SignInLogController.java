package com.monolithiot.iot.user.web.controller.safe;

import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.user.entity.SignInLog;
import com.monolithiot.iot.user.service.general.SignInLogService;
import com.monolithiot.iot.user.service.listener.SignInListener;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by 郭文梁 2019/11/11 18:20
 * SignInLogController
 * 签到相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/11/11 18:20
 */
@RestController
@RequestMapping("/sign-in-log")
public class SignInLogController extends AbstractEntityController<SignInLog> {
    private final SignInLogService signInLogService;
    private final SignInListener signInListener;

    /**
     * 构造时指定业务组件
     *
     * @param signInLogService 业务组件
     */
    public SignInLogController(SignInLogService signInLogService,
                               SignInListener signInListener) {
        super(signInLogService);
        this.signInLogService = signInLogService;
        this.signInListener = signInListener;
    }

    /**
     * 登录
     *
     * @param request 请求对象
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<SignInLog> signIn(HttpServletRequest request) {
        val userId = requireCurrentUserId(request);
        val res = signInLogService.signIn(userId, signInListener);
        return GeneralResult.ok(res);
    }

    /**
     * 我的签到记录
     *
     * @param month   月份
     * @param year    年
     * @param request 请求都西昂
     * @return GR
     */
    @GetMapping("/_mine")
    public GeneralResult<List<SignInLog>> mineLog(@RequestParam("year") Integer year,
                                                  @RequestParam("month") Integer month,
                                                  HttpServletRequest request) {
        val userId = requireCurrentUserId(request);
        val logList = signInLogService.findByUserAndMonth(userId, year, month);
        return GeneralResult.ok(logList);
    }
}
