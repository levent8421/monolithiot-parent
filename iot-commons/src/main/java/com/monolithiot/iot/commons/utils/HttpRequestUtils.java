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

    /**
     * 从请求中获取登录名
     *
     * @param request 请求
     * @return 登录名
     */
    public static String obtainLoginNameFromRequest(HttpServletRequest request) {
        return request.getHeader(ApplicationConstants.Router.LOGIN_NAME_HEADER_NAME);
    }

    /**
     * 从请求中获取用户ID
     *
     * @param request 请求
     * @return 用户ID
     */
    public static Integer obtainUserIdFromtRequest(HttpServletRequest request) {
        final String idStr = request.getHeader(ApplicationConstants.Router.USER_ID_HEADER_NAME);
        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
