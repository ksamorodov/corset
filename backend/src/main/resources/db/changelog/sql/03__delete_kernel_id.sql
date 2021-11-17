alter table constructions drop column kernel_id;
alter table kernels add constructions_id int not null references constructions(id);