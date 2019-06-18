package com.monolithiot.iot.user.web.advice;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import com.monolithiot.iot.commons.exception.PermissionDeniedException;
import com.monolithiot.iot.commons.exception.ResourceNotFoundException;
import com.monolithiot.iot.commons.vo.GeneralResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Create by 郭文梁 2019/4/20 0020 18:42
 * ExceptionControllerAdvice
 * 控制器的异常统一处理
 *
 * @author 郭文梁
 * @data 2019/4/20 0020
 */
@Slf4j
@ControllerAdvice(basePackages = {"com.monolithiot.iot.user.web.controller.safe", "com.monolithiot.iot.user.web.controller.open"})
public class ExceptionControllerAdvice {
    /**
     * 处理ResourceNotFoundException异常
     *
     * @param e 异常对象
     * @return 返回内容
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GeneralResult<?> notFound(ResourceNotFoundException e) {
        return GeneralResult.notFound(e.getMessage());
    }

    /**
     * 处理InternalServerErrorException 异常
     *
     * @param e 异常对象
     * @return GeneralResult
     */
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralResult<?> error(InternalServerErrorException e) {
        log.error("Internal Server Error", e);
        return GeneralResult.error(e.getMessage());
    }

    /**
     * 处理BadRequestException异常
     *
     * @param e 异常
     * @return GR
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResult<?> badRequest(BadRequestException e) {
        return GeneralResult.badRequest(e.getMessage());
    }

    /**
     * 处理PermissionDeniedException异常
     *
     * @param e 异常
     * @return GR
     */
    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GeneralResult<?> permissionDenied(PermissionDeniedException e) {
        return GeneralResult.permissionDenied(e.getMessage());
    }
}
