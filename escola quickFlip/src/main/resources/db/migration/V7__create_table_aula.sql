create table aula(
    id serial not null primary key,
    horario_inicio timestamp not null,
    horario_fim timestamp not null,
    ativo boolean not null,
    id_disciplina integer not null,
    foreign key (id_disciplina) references disciplina (id)
);