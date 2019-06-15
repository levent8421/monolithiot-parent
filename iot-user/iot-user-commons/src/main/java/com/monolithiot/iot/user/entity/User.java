package com.monolithiot.iot.user.entity;

import com.monolithiot.iot.commons.entity.AbstractIntegerIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Create by 郭文梁 2019/6/15 0015 14:47
 * User
 * 用户实体类
 *
 * @author 郭文梁
 * @data 2019/6/15 0015
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_user")
@Data
public class User extends AbstractIntegerIdEntity {
    /**
     * 用户名
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 密码
     */
    @Column(name = "password", nullable = false)
    private String password;
    /**
     * 电话
     */
    @Column(name = "phone", length = 100, nullable = false)
    private String phone;
    /**
     * 邮箱
     */
    @Column(name = "email", nullable = false)
    private String email;
    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;
    /**
     * 性别
     */
    @Column(name = "gender", length = 1, nullable = false)
    private Integer gender;
    /**
     * 地址：省
     */
    @Column(name = "province", length = 100)
    private String province;
    /**
     * 地址：市
     */
    @Column(name = "city", length = 100)
    private String city;
    /**
     * 地址：区
     */
    @Column(name = "district", length = 100)
    private String district;
    /**
     * 详细地址
     */
    @Column(name = "address")
    private String address;
    /**
     * 行业
     */
    @Column(name = "industry")
    private String industry;
}
