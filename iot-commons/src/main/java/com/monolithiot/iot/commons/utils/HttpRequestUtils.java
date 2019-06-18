package com.monolithiot.iot.commons.utils;

import com.monolithiot.iot.commons.context.ApplicationConstants;
import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Create by 郭文梁 2019/6/18 0018 16:00
 * HttpRequestUtils
 * HttpRequest相关工具类
 *
 * @author 郭文梁
 * @data 2019/6/18 0018
 */
public class HttpRequestUtils {
    /**
     * 读取HttpServletRequest的请求体为String
     *
     * @param request 请求对象
     * @return 请求体
     */
    public static String stringBody(HttpServletRequest request) {
        try (final ServletInputStream inputStream = request.getInputStream()) {
            return IOUtils.toString(inputStream, ApplicationConstants.DEFAULT_CHARSET);
        } catch (IOException e) {
            throw new InternalServerErrorException("Could not get inputStream from Request!", e);
        }
    }
}
