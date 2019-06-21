package com.monolithiot.iot.commons.utils;

import com.monolithiot.iot.commons.exception.InternalServerErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Function;

/**
 * Create by 郭文梁 2019/6/21 0021 13:41
 * CommonIOUtils
 * IO工具类
 *
 * @author 郭文梁
 * @data 2019/6/21 0021
 */
public class CommonIOUtils {
    /**
     * 将操作转换为bytes
     *
     * @param fn 操作函数
     * @return bytes
     */
    public static byte[] asByteArray(Function<OutputStream, Void> fn) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            fn.apply(out);
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new InternalServerErrorException("Could not create ByteArrayOutputStream!", e);
        }
    }
}
