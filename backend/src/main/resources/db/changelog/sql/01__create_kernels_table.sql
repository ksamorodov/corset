create sequence if not exists kernels_seq start 1;

create table if not exists kernels (
    id int not null default nextval('kernels_seq' :: regclass),
    kernel_size numeric,
    crossSectionalArea numeric,
    elasticModulus numeric,
    allowableStress numeric,
    concentratedLoad numeric,
    linearVoltage numeric
);

alter table kernels  add constraint kernels_pk primary key (id);