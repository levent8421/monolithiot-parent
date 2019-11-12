package com.monolithiot.iot.user.repository;

import com.monolithiot.iot.repository.AbstractMapper;
import com.monolithiot.iot.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Create by 郭文梁 2019/6/15 0015 16:10
 * UserMapper
 * 用户相关数据库访问组件
 *
 * @author 郭文梁
 * @data 2019/6/15 0015
 */
@Repository
@Mapper
public interface UserMapper extends AbstractMapper<User> {
    /**
     * 通过登录名查询用户
     *
     * @param loginName 登录名
     * @return 用户
     */
    User selectByLoginName(@Param("loginName") String loginName);

    /**
     * 通过用户名或电话或邮箱查询用户
     *
     * @param name  用户名
     * @param phone 电话
     * @param email 邮箱
     * @return User
     */
    User selectByNameOrPhoneOrEmail(@Param("name") String name,
                                    @Param("phone") String phone,
                                    @Param("email") String email);

    /**
     * 递增连续签到天数
     *
     * @param userId 用户ID
     * @param amount 递增值
     * @return rows
     */
    int incConsecutiveSignInCount(@Param("userId") Integer userId,
                                  @Param("amount") int amount);

    /**
     * Update user`s consecutiveSignInCount by userId
     *
     * @param userId userId
     * @param value  value
     * @return rows
     */
    int updateConsecutiveSignInCountById(@Param("userId") int userId,
                                         @Param("value") int value);

    /**
     * Increment User`s pointScore
     *
     * @param userId userId
     * @param score  score
     * @return rows
     */
    int incPointScore(@Param("userId") int userId,
                      @Param("score") int score);
}
