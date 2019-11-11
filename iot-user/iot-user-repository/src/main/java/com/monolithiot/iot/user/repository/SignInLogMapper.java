package com.monolithiot.iot.user.repository;

import com.monolithiot.iot.repository.AbstractMapper;
import com.monolithiot.iot.user.entity.SignInLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Create by 郭文梁 2019/11/11 18:08
 * SignInLogMapper
 * 登录记录相关数据库访问组件
 *
 * @author 郭文梁
 * @data 2019/11/11 18:08
 */
@Repository
public interface SignInLogMapper extends AbstractMapper<SignInLog> {
    /**
     * 查询用户某天的登录记录
     *
     * @param date   日期
     * @param userId 用户ID
     * @return SignInLog
     */
    SignInLog selectDateAndUser(@Param("date") Date date,
                                @Param("userId") int userId);

    /**
     * 查询用户某月的全部签到记录
     *
     * @param userId 用户ID
     * @param year   year
     * @param month  month
     * @return Log List
     */
    List<SignInLog> selectByUserAndMonth(@Param("userId") Integer userId,
                                         @Param("year") int year,
                                         @Param("month") int month);
}
