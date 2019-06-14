package com.monolithiot.iot.commons.entity;

import com.monolithiot.iot.commons.context.ApplicationConstants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create By leven ont 2019/6/14 22:46
 * Class Name :[AbstractEntity]
 * <p>
 * 系统实体类通用代码封装
 *
 * @author leven
 */
@Data
public abstract class AbstractEntity<IdType> {
    /**
     * ID
     */
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(generator = ApplicationConstants.Sql.GENERATOR_JDBC)
    private IdType id;
    /**
     * 对象创建时间
     */
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    /**
     * 对象更新时间
     */
    @Column(name = "update_time", nullable = false)
    private Date updateTime;
    /**
     * 是否已经标记为删除
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
