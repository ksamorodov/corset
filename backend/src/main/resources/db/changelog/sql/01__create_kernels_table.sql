create sequence if not exists kernels_seq start 1;

create table if not exists kernels (
    id int not null default nextval('kernels_seq' :: regclass),
    kernel_size numeric,
    cross_sectional_area numeric,
    elastic_modulus numeric,
    allowable_stress numeric,
    concentrated_load numeric,
    linear_voltage numeric
);

alter table kernels  add constraint kernels_pk primary key (id);