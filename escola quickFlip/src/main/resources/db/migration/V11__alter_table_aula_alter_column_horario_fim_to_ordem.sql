alter table aula rename column horario_fim to ordem;
alter table aula alter column ordem type varchar(100);