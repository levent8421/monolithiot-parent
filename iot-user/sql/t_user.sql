drop table if exists t_user;

create table t_user
(
    id          int(10)      not null auto_increment primary key,
    name        varchar(255) not null,
    password    varchar(255) not null,
    phone       varchar(100) null,
    email       varchar(255) null,
    avatar      varchar(255) null,
    gender      tinyint(1)   not null,
    province    varchar(100) null,
    city        varchar(100) null,
    district    varchar(100) null,
    address     varchar(255) null,
    industry    varchar(255) null,
    create_time datetime     not null,
    update_time datetime     not null,
    deleted     bit          not null
);

select u.id          u_id,
       u.name        u_name,
       u.password    u_password,
       u.phone       u_phone,
       u.email       u_email,
       u.avatar      u_avatar,
       u.gender      u_gender,
       u.province    u_province,
       u.city        u_city,
       u.district    u_district,
       u.address     u_address,
       u.industry    u_industry,
       u.create_time u_create_time,
       u.update_time u_update_time,
       u.deleted     u_deleted
from t_user as u
where u.deleted = false;

alter table t_user
    add column nickname varchar(255) null after name;

alter table t_user
    add column point_score int(10) null after industry;

alter table t_user
    add column consecutive_sign_in_count int(5) not null default 0 after point_score;

alter table t_user
    modify consecutive_sign_in_count int(5) null after point_score;