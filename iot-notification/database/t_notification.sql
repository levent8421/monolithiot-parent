drop table if exists t_notification;

create table t_notification
(
    id          int(10)      not null auto_increment primary key,
    trace_no    varchar(255) not null,
    type        int(2)       not null,
    target      varchar(255) null,
    user_id     int(10)      null,
    code        varchar(255) null,
    extend_data text         null,
    expire_in   int(10)      null,
    create_time datetime     not null,
    update_time datetime     not null,
    deleted     bit          not null
);

select n.id          n_id,
       n.trace_no    n_trace_no,
       n.type        n_type,
       n.target      n_target,
       n.user_id     n_user_id,
       n.code        n_code,
       n.extend_data n_extend_data,
       n.expire_in   n_expire_in,
       n.create_time n_create_time,
       n.update_time n_update_time,
       n.deleted     n_deleted
from t_notification as n;