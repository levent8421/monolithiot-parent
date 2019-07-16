package com.monolithiot.iot.templates.service.impl;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import com.monolithiot.iot.commons.utils.MultipartFileUtil;
import com.monolithiot.iot.templates.service.StaticFileService;
import com.monolithiot.iot.templates.service.prop.PathProp;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Create By leven ont 2019/7/16 23:39
 * Class Name :[StaticFileServiceImpl]
 * <p>
 * 静态文件业务行为实现
 *
 * @author leven
 */
@Service
@Slf4j
public class StaticFileServiceImpl implements StaticFileService {
    private final PathProp pathProp;

    public StaticFileServiceImpl(PathProp pathProp) {
        this.pathProp = pathProp;
    }

    @Override
    public String saveTemplateImageFile(MultipartFile file, Integer userId) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("文件未上传！");
        }
        val rootPath = pathProp.getStaticFilePath();
        val imagePath = pathProp.getTemplateImagePath() + userId + "/";
        val filePath = rootPath + imagePath;
        try {
            val filename = MultipartFileUtil.save(file, filePath);
            val accessUrl = pathProp.getStaticServerPrefix() + imagePath + filename;
            log.debug("Template image file save success! path=[{}], url=[{}]", filePath, accessUrl);
            return accessUrl;
        } catch (IOException e) {
            throw new InternalServerErrorException("保存文件失败[IOE]," + e.getMessage(), e);
        }
    }
}
