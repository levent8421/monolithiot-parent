drop table if exists t_user_measure_data;
create table t_user_measure_data
(
    id          int(10)  not null auto_increment primary key,
    user_id     int(10)  not null,
    create_time datetime not null,
    update_time datetime not null,
    deleted     bit      not null
) engine = 'Innodb'
  charset utf8;