package com.monolithiot.iot.templates.controller.safe;

import com.alibaba.fastjson.JSON;
import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.exception.PermissionDeniedException;
import com.monolithiot.iot.commons.utils.CollectionUtils;
import com.monolithiot.iot.commons.utils.HttpRequestUtils;
import com.monolithiot.iot.commons.utils.TextUtils;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.templates.entity.TemplateGroup;
import com.monolithiot.iot.templates.service.TemplateGroupService;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.monolithiot.iot.commons.utils.ParamChecker.notEmpty;
import static com.monolithiot.iot.commons.utils.ParamChecker.notNull;

/**
 * Create by 郭文梁 2019/7/25 17:43
 * TemplateGroupController
 * 模板分组相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/7/25 17:43
 */
@RestController
@RequestMapping("/group")
public class TemplateGroupController extends AbstractEntityController<TemplateGroup> {
    private final TemplateGroupService templateGroupService;

    private TemplateGroupController(TemplateGroupService templateGroupService) {
        super(templateGroupService);
        this.templateGroupService = templateGroupService;
    }

    /**
     * 创建模板分组
     *
     * @param request 请求对象
     * @param param   参数
     * @return GR
     */

    @PutMapping("/")
    public GeneralResult<TemplateGroup> create(HttpServletRequest request,
                                               @RequestBody TemplateGroup param) {

        val userId = HttpRequestUtils.obtainUserIdFromtRequest(request);
        val group = new TemplateGroup();
        checkAndCopyCreateParam(param, group);
        val res = templateGroupService.create(userId, group);
        return GeneralResult.ok(res);
    }

    /**
     * 检查并拷贝创建模板分组的参数
     *
     * @param param  参数
     * @param target 拷贝地址
     */
    private void checkAndCopyCreateParam(TemplateGroup param, TemplateGroup target) {
        val ex = BadRequestException.class;
        notNull(param, ex, "参数未传！");
        notEmpty(param.getName(), ex, "分组名称必填！");

        target.setName(param.getName());
        target.setDescription(param.getDescription());

        val templateIds = param.getTemplates() == null ?
                new ArrayList<String>() :
                param.getTemplates().stream().filter(TextUtils::isTrimedNotEmpty).collect(Collectors.toList());
        target.setTemplates(templateIds);
    }

    /**
     * 更新分组信息
     *
     * @param request 请求对象
     * @param id      分组ID
     * @param param   参数
     * @return GR
     */
    @PostMapping("/{id}")
    public GeneralResult<TemplateGroup> update(HttpServletRequest request,
                                               @PathVariable("id") Integer id,
                                               @RequestBody TemplateGroup param) {
        val userId = getCurrentUserId(request);
        val group = templateGroupService.require(id);
        checkPermission(userId, group);
        checkAndCopyUpdateParam(param, group);
        val res = templateGroupService.updateById(group);
        return GeneralResult.ok(res);
    }

    /**
     * 检查并拷贝更新参数
     *
     * @param param  参数
     * @param target 拷贝目标
     */
    private void checkAndCopyUpdateParam(TemplateGroup param, TemplateGroup target) {
        val ex = BadRequestException.class;
        notNull(param, ex, "参数未传！");
        notEmpty(param.getName(), ex, "分组名称必填！");

        target.setName(param.getName());
        target.setDescription(param.getDescription());
    }

    /**
     * 为分组添加模板
     *
     * @param request 请求
     * @param id      分组ID
     * @param param   参数
     * @return GR
     */
    @PostMapping("/{id}/append-templates")
    public GeneralResult<TemplateGroup> appendTemplateId(HttpServletRequest request,
                                                         @PathVariable("id") Integer id,
                                                         @RequestBody List<String> param) {
        val userId = getCurrentUserId(request);
        val group = templateGroupService.require(id);
        checkPermission(userId, group);

        val ex = BadRequestException.class;
        notNull(param, ex, "参数未传!");

        val res = templateGroupService.appendTemplate(group,
                param.stream().filter(TextUtils::isTrimedNotEmpty).collect(Collectors.toList()));
        return GeneralResult.ok(res);
    }

    /**
     * 检查用户是否有操作分组的权限
     *
     * @param userId 用户ID
     * @param group  分组
     */
    private void checkPermission(Integer userId, TemplateGroup group) {
        if (!Objects.equals(userId, group.getUserId())) {
            throw new PermissionDeniedException("您无权操作该数据！");
        }
    }

    /**
     * 重置模板分组的模板列表
     *
     * @param request 请求对象
     * @param id      分组ID
     * @param param   参数
     * @return GR
     */
    @PostMapping("/{id}/replace-templates")
    public GeneralResult<TemplateGroup> replaceTemplates(HttpServletRequest request,
                                                         @PathVariable("id") Integer id,
                                                         @RequestBody List<String> param) {
        val userId = getCurrentUserId(request);
        val group = templateGroupService.require(id);
        checkPermission(userId, group);

        val ex = BadRequestException.class;
        notNull(param, ex, "参数未传!");
        val templateIds = CollectionUtils
                .deDuplication(param)
                .stream()
                .filter(TextUtils::isTrimedNotEmpty)
                .collect(Collectors.toList());
        group.setTemplatesJson(JSON.toJSONString(templateIds));
        templateGroupService.updateById(group);
        return GeneralResult.ok(group);
    }
}
