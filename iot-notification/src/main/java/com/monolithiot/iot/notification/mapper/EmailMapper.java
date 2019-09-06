package com.monolithiot.iot.notification.mapper;

import com.monolithiot.iot.notification.entity.Email;
import com.monolithiot.iot.repository.AbstractMapper;
import org.springframework.stereotype.Repository;

/**
 * Create by 郭文梁 2019/9/6 9:22
 * EmailMapper
 * Email Repository implement by Mybatis
 *
 * @author 郭文梁
 * @data 2019/9/6 9:22
 */
@Repository
public interface EmailMapper extends AbstractMapper<Email> {
}
