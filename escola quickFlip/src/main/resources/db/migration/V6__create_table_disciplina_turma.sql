create table disciplina_turma(
    id serial not null primary key,
    id_disciplina integer not null,
    id_turma integer not null,
    foreign key (id_disciplina) references disciplina (id),
    foreign key (id_turma) references turma (id)
);