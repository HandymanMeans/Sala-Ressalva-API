use sala_ressalva;
 create table sala(
id_sala BIGINT AUTO_INCREMENT primary KEY,
nome VARCHAR(256) not null,
departamento VARCHAR(256) not null,
descricao VARCHAR(128),
disponivel boolean default true 
);