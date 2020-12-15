package com.monolithiot.iot.commons.exception;

import lombok.Getter;

/**
 * Create by 郭文梁 2019/4/22 0022 15:40
 * BadRequestException
 * 请求客户端异常
 *
 * @author 郭文梁
 * @data 2019/4/22 0022
 */
public class BadRequestException extends RuntimeException {
    @Getter
    private String[] args;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String... args) {
        super(message);
        this.args = args;
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
