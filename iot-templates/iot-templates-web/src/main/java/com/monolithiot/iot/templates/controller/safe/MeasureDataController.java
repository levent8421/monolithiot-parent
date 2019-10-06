package com.monolithiot.iot.templates.controller.safe;

import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.templates.entity.MeasureData;
import com.monolithiot.iot.templates.service.MeasureDataService;
import com.monolithiot.iot.templates.util.MeasureDataUtils;
import com.monolithiot.iot.web.controller.AbstractController;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by 郭文梁 2019/8/20 16:16
 * MeasureDataController
 * 测量数据相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/8/20 16:16
 */
@RestController
@RequestMapping("/measure-data")
public class MeasureDataController extends AbstractController {
    private final MeasureDataService measureDataService;

    public MeasureDataController(MeasureDataService measureDataService) {
        this.measureDataService = measureDataService;
    }

    /**
     * 创建测量数据
     *
     * @param param   参数
     * @param request 请求对象
     * @return GR with MeasureData object
     */
    @PutMapping("/")
    public GeneralResult<MeasureData> create(@RequestBody MeasureData param,
                                             HttpServletRequest request) {
        val userId = requireCurrentUserId(request);
        val userName = requireCurrentUserName(request);

        val measureData = new MeasureData();
        MeasureDataUtils.checkCreateParam(param, measureData);
        measureData.setAuthorId(userId);
        measureData.setAuthorName(userName);

        val res = measureDataService.save(measureData);
        return GeneralResult.ok(res);
    }

    /**
     * 通过ID查询测量数据
     *
     * @param id ID
     * @return GR with measureData Object
     */
    @GetMapping("/{id}")
    public GeneralResult<MeasureData> findMeasureDataById(@PathVariable("id") String id) {
        MeasureData measureData = measureDataService.require(id);
        return GeneralResult.ok(measureData);
    }

    /**
     * 查询当前用户的测量数据
     *
     * @param request 请求对象
     * @param page    页码
     * @param rows    每页大小
     * @return GR
     */
    @GetMapping("/mine")
    public GeneralResult<Page<MeasureData>> findByCurrentUser(HttpServletRequest request, Integer page, Integer rows) {
        page = defaultPage(page);
        rows = defaultRows(rows);
        Integer userId = requireCurrentUserId(request);
        Page<MeasureData> res = measureDataService.findByUserId(userId, page, rows);
        return GeneralResult.ok(res);
    }

    /**
     * 查询当前用户所有测量数据的Id
     *
     * @param page page
     * @param rows rows
     * @return GR
     */
    @GetMapping("/mine/id-list")
    public GeneralResult<List<String>> findDataIdsByCurrentUser(HttpServletRequest request,
                                                                Integer page, Integer rows) {
        page = defaultPage(page);
        rows = defaultRows(rows);
        val userId = requireCurrentUserId(request);
        Page<MeasureData> res = measureDataService.findByUserId(userId, page, rows);
        val idList = res.getContent().stream().map(MeasureData::getId).collect(Collectors.toList());
        return GeneralResult.ok(idList);
    }
}
