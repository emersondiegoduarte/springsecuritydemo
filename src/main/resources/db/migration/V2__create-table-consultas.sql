create table consultas(

    id bigserial not null,
    medico_id bigint not null,
    paciente varchar(100) not null,
    data timestamp not null,

    primary key(id),
    constraint fk_consultas_medico_id foreign key(medico_id) references medicos(id)

);