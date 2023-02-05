create table turma(
    id serial not null primary key,
    codigo char(2) not null,
    periodo varchar(100) not null,
    serie varchar(100) not null,
    ativo boolean not null
);