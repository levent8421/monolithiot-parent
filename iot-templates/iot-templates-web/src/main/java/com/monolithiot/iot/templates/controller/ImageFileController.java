package com.monolithiot.iot.templates.controller;

import com.monolithiot.iot.commons.vo.GeneralResult;
import com.monolithiot.iot.web.controller.AbstractController;
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
    /**
     * 上传图片
     *
     * @param file    图片文件
     * @param request 请求对象
     * @return GR with access url
     */
    @PostMapping("/upload")
    public GeneralResult<String> upload(MultipartFile file, HttpServletRequest request) {
        return null;
    }
}
