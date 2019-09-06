drop table if exists t_email;

create table t_email
(
    id           int(10)      not null auto_increment primary key,
    trace_id     varchar(255) not null,
    user_id      int(10)      null,
    target       varchar(255) not null,
    subject      varchar(255) not null,
    content_text text         null,
    intention    int(2)       not null,
    create_time  datetime     not null,
    update_time  datetime     not null,
    deleted      bit          not null
) engine = 'InnoDb'
  charset utf8;


select e.id           e_id,
       e.trace_id     e_trace_id,
       e.user_id      e_user_id,
       e.target       e_target,
       e.subject      e_subject,
       e.content_text e_content_text,
       e.intention    e_intention,
       e.create_time  e_create_time,
       e.update_time  e_update_time,
       e.deleted      e_deleted
from t_email as e
where (e.deleted = false);