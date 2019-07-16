package com.monolithiot.iot.templates.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Create By leven ont 2019/7/16 23:37
 * Class Name :[StaticFileService]
 * <p>
 * 静态文件相关业务行为定义
 *
 * @author leven
 */
public interface StaticFileService {
    /**
     * 保存上传的模板文件到
     *
     * @param file   文件
     * @param userId 用户ID（用于文件分组）
     * @return 访问URL
     */
    String saveTemplateImageFile(MultipartFile file, Integer userId);
}
