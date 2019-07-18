drop table if exists t_user_template;
create table t_user_template
(
    id          int(10)    not null auto_increment primary key,
    user_id     int(10)    not null,
    template_id bigint(20) not null,
    create_time datetime   not null,
    update_time datetime   not null,
    deleted     bit        not null
);

select ut.id          ut_id,
       ut.user_id     ut_user_id,
       ut.template_id ut_template_id,
       ut.create_time ut_create_time,
       ut.update_time ut_update_time,
       ut.deleted     ut_deleted
from t_user_template as ut
where ut.deleted = false;