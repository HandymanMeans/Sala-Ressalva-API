use sala_ressalva;
CREATE TABLE reserva (
    id BIGINT AUTO_INCREMENT primary KEY,
    usuario_id BIGINT NOT NULL,
    sala_id BIGINT NOT NULL,
    data_pedido DATETIME NOT NULL,
    data_reserva DATETIME NOT NULL,
    cancelada BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_sala FOREIGN KEY (sala_id) REFERENCES sala(id_sala) ON DELETE CASCADE
);
