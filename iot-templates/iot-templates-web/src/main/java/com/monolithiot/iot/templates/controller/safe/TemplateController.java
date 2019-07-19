package com.monolithiot.iot.templates.controller.safe;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.utils.ParamChecker;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.templates.entity.MeasureTemplate;
import com.monolithiot.iot.templates.service.TemplateService;
import com.monolithiot.iot.web.controller.AbstractController;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 郭文梁 2019/7/19 17:22
 * TemplateController
 * 模板相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/7/19 17:22
 */
@RestController
@RequestMapping("/template")
public class TemplateController extends AbstractController {
    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    /**
     * 搜索模板
     *
     * @param q    搜索字符串
     * @param page 页码
     * @param rows 每页大小
     * @return GR with spring data page
     */
    @GetMapping("/search")
    public GeneralResult<Page<MeasureTemplate>> search(@RequestParam("q") String q,
                                                       Integer page, Integer rows) {
        ParamChecker.notEmpty(q, BadRequestException.class, "请输入搜索内容！");
        page = defaultPage(page);
        rows = defaultRows(rows);

        val res = templateService.search(q, page, rows);
        return GeneralResult.ok(res);
    }
}
