alter table aula add id_turma integer not null;
alter table aula add constraint fk_id_turma foreign key (id_turma) references turma(id);