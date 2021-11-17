create sequence if not exists constructions_seq start 1;

create table if not exists constructions (
    id int not null default nextval('constructions_seq' :: regclass),
    kernel_id int not null references kernels(id),
    left_support boolean not null,
    right_support boolean not null
    );

alter table constructions  add constraint constructions_pk primary key (id);