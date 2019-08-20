drop table if exists t_industry;
create table t_industry
(
    id          int(10)      not null auto_increment primary key,
    name        varchar(255) not null comment '行业名称',
    create_time datetime     not null,
    update_time datetime     not null,
    deleted     bit          not null
) engine = 'InnoDb'
  charset utf8;