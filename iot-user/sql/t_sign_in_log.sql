drop table if exists t_sign_in_log;

create table t_sign_in_log
(
    id           int(10)  not null auto_increment primary key,
    user_id      int(10)  not null,
    sign_in_date date     not null,
    create_time  datetime not null,
    update_time  datetime not null,
    deleted      bit(1)   not null
);

select sil.id           sil_id,
       sil.user_id      sil_user_id,
       sil.sign_in_date sil_sign_in_date,
       sil.create_time  sil_create_time,
       sil.update_time  sil_update_time,
       sil.deleted      sil_deleted
from t_sign_in_log as sil
where sil.deleted = false;