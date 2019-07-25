drop table if exists t_template_group;

create table t_template_group
(
    id             int(10)      not null auto_increment primary key,
    user_id        int(10)      null,
    name           varchar(255) not null,
    description    varchar(255) not null,
    templates_json text         null,
    create_time    datetime     not null,
    update_time    datetime     not null,
    deleted        bit          not null
);

select tg.id             tg_id,
       tg.user_id        tg_user_id,
       tg.name           tg_name,
       tg.description    tg_description,
       tg.templates_json tg_templates_json,
       tg.create_time    tg_create_time,
       tg.update_time    tg_update_time,
       tg.deleted        tg_deleted
from t_template_group as tg
where tg.deleted = false;