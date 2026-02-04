create table pacientes(
    id bigserial not null,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    telefone varchar(100) not null unique,
    cpf varchar(14) not null unique,

    primary key(id)
);
