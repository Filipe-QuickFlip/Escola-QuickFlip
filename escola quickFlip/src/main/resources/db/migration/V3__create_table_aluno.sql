create table aluno(
    id serial not null primary key,
    nome varchar(250) not null,
    matricula char(6) not null unique,
    cpf char(11) not null unique,
    telefone varchar(11) not null,
    email varchar(250) not null,
    logradouro varchar(250) not null,
    numero integer not null,
    complemento varchar(100),
    bairro varchar(250) not null,
    cidade varchar(250) not null,
    uf char(2) not null,
    cep char(8) not null,
    ativo boolean not null,
    id_turma integer,
    foreign key (id_turma) references turma(id)
);