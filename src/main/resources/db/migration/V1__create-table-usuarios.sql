create table usuarios(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    nivel_da_conta int(100) not null,
    materia_favorita varchar(100) not null,

    primary key(id)

);