create sequence if not exists test_seq start 1;

create table if not exists test (
    id int not null default nextval('test_seq' :: regclass),
    string varchar (255),
    digit int
);

alter table test  add constraint test_pk primary key (id);