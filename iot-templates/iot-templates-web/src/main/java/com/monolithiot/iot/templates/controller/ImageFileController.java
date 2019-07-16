package com.monolithiot.iot.templates.controller;

import com.monolithiot.iot.commons.utils.HttpRequestUtils;
import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.templates.service.StaticFileService;
import com.monolithiot.iot.web.controller.AbstractController;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by 郭文梁 2019/7/16 18:53
 * ImageFileController
 * 图片文件相关数据访问控制器
 *
 * @author 郭文梁
 * @data 2019/7/16 18:53
 */
@RestController
@RequestMapping("/api/image")
public class ImageFileController extends AbstractController {
    private final StaticFileService staticFileService;

    public ImageFileController(StaticFileService staticFileService) {
        this.staticFileService = staticFileService;
    }

    /**
     * 上传图片
     *
     * @param file    图片文件
     * @param request 请求对象
     * @return GR with access url
     */
    @PostMapping("/upload")
    public GeneralResult<String> upload(MultipartFile file, HttpServletRequest request) {
        val userId = HttpRequestUtils.obtainUserIdFromtRequest(request);
        val accessUrl = staticFileService.saveTemplateImageFile(file, userId);
        return GeneralResult.ok(accessUrl);
    }
}
