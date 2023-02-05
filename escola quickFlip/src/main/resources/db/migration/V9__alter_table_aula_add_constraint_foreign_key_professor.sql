alter table aula add id_professor integer not null;
alter table aula add constraint fk_id_professor foreign key (id_professor) references professor(id);