drop table if exists t_sms_verification_code;


create table t_sms_verification_code
(
    id                    int(10)      not null auto_increment primary key,
    trace_no              varchar(255) not null,
    target                varchar(255) not null,
    user_id               int(10)      null,
    verification_code     varchar(100) null,
    pre_verification_code varchar(100) null,
    extend_data           text         null,
    expire_in             int(10)      null,
    state                 int(2)       not null,
    create_time           datetime     not null,
    update_time           datetime     not null,
    deleted               bit          not null
);

select svc.id                    svc_id,
       svc.trace_no              svc_trace_no,
       svc.target                svc_target,
       svc.user_id               svc_user_id,
       svc.verification_code     svc_verification_code,
       svc.pre_verification_code svc_pre_verification_code,
       svc.extend_data           svc_extend_data,
       svc.expire_in             svc_expire_in,
       svc.state                 avc_state,
       svc.create_time           svc_create_time,
       svc.update_time           svc_update_time,
       svc.deleted               svc_deleted
from t_sms_verification_code as svc
where svc.deleted = false;