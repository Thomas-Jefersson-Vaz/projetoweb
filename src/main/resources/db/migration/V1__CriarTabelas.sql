create table users
(
    id              serial
        primary key,
    name            varchar(100),
    email           varchar(100) not null
        constraint users_email_unique
            unique,
    password        varchar(255),
    role            varchar(50),
    data_nascimento date,
    nacionalidade   varchar(100),
    num_telefone    varchar(20),
    cidade          varchar(100),
    bio             text,
    assento         varchar(50),
    comida          varchar(50),
    classe          varchar(50),
    moeda           varchar(10)
);