create sequence app_user_seq;

create table app_user (
 id bigint not null,
 name varchar(100) not null,
 created TIMESTAMP(9) WITH TIME ZONE not null,
 constraint user_pk primary key (id)
);