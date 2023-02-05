create table disciplina(
    id serial not null primary key,
    nome varchar(200) not null unique,
    carga_horaria numeric(38,2) not null,
    ativo boolean not null
);