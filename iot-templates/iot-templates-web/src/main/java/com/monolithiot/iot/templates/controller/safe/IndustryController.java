package com.monolithiot.iot.templates.controller.safe;

import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.templates.entity.Industry;
import com.monolithiot.iot.templates.service.IndustryService;
import com.monolithiot.iot.web.advice.AbstractEntityController;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Create By leven ont 2019/8/26 21:47
 * Class Name :[IndustryController]
 * <p>
 * 行业信息相关数据访问控制器
 *
 * @author leven
 */
@RestController
@RequestMapping("/industry")
public class IndustryController extends AbstractEntityController<Industry> {
    private final IndustryService industryService;

    /**
     * 构造时指定业务组件
     *
     * @param service 业务组件
     */
    public IndustryController(IndustryService service) {
        super(service);
        this.industryService = service;
    }

    /**
     * 查询所有行业信息
     *
     * @return GR with industry List
     */
    @GetMapping("/")
    public GeneralResult<List<Industry>> findAll() {
        val all = industryService.findAllWithOrder();
        return GeneralResult.ok(all);
    }

    /**
     * 通过ID查找行业信息
     *
     * @param id ID
     * @return GR
     */
    @GetMapping("/{id}")
    public GeneralResult<Industry> findById(@PathVariable("id") Integer id) {
        @NotNull Industry industry = industryService.require(id);
        return GeneralResult.ok(industry);
    }
}

