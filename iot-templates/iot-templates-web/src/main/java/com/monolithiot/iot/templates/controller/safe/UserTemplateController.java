package com.monolithiot.iot.templates.controller.safe;

import com.monolithiot.iot.commons.utils.HttpRequestUtils;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.templates.entity.MeasureTemplate;
import com.monolithiot.iot.templates.entity.UserTemplate;
import com.monolithiot.iot.templates.service.UserTemplateService;
import com.monolithiot.iot.templates.util.MeasureDataUtils;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Create By leven ont 2019/7/18 21:53
 * Class Name :[UserTemplateController]
 * <p>
 * 用户模板相关数据访问控制器 需授权访问
 *
 * @author leven
 */
@RestController
@RequestMapping("/user-template")
public class UserTemplateController extends AbstractEntityController<UserTemplate> {
    private final UserTemplateService userTemplateService;

    /**
     * 构造时指定业务组件
     *
     * @param service 业务组件
     */
    public UserTemplateController(UserTemplateService service) {
        super(service);
        this.userTemplateService = service;
    }

    /**
     * 创建模板
     *
     * @param request 请求对象
     * @param param   请求参数
     * @return GR
     */
    @PutMapping("/")
    public GeneralResult<UserTemplate> create(HttpServletRequest request,
                                              @RequestBody MeasureTemplate param) {
        val template = new MeasureTemplate();
        MeasureDataUtils.checkCreateParam(param, template);

        val userId = HttpRequestUtils.obtainUserIdFromtRequest(request);
        val userName = HttpRequestUtils.obtainLoginNameFromRequest(request);
        template.setAuthorId(userId);
        template.setAuthorName(userName);

        val res = userTemplateService.create(userId, template);
        return GeneralResult.ok(res);
    }
}
