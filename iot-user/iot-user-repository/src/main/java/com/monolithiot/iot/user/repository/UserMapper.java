package com.monolithiot.iot.user.repository;

import com.monolithiot.iot.repository.AbstractMapper;
import com.monolithiot.iot.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
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
}
