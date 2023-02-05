create table disciplina_professor(
    id serial not null primary key,
    id_disciplina integer not null,
    id_professor integer not null,
    foreign key (id_disciplina) references disciplina (id),
    foreign key (id_professor) references professor (id)
);