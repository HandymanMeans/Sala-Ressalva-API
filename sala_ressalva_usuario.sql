 use sala_ressalva;
 create table usuario (
id_usuario BIGINT auto_increment primary key,
nome VARCHAR(256) not null,
email VARCHAR(256) not null,
telefone_celular VARCHAR(17) not null,
cpf VARCHAR(14) not null unique
);