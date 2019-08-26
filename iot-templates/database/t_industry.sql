drop table if exists t_industry;
create table t_industry
(
    id          int(10)      not null auto_increment primary key,
    name        varchar(255) not null comment '行业名称',
    description varchar(255) null comment '备注',
    order_num   int(10)      not null,
    create_time datetime     not null,
    update_time datetime     not null,
    deleted     bit          not null
) engine = 'InnoDb'
  charset utf8;

select i.id          i_id,
       i.name        i_name,
       i.description i_description,
       i.order_num   i_order_num,
       i.create_time i_create_time,
       i.update_time i_update_time,
       i.deleted     i_deleted
from t_industry as i
where i.deleted = false;