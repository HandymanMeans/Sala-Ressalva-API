use sala_ressalva;
 insert into  usuarios (id, nome, email, idfone, cpf) VALUES
(1, 'João Silva', 'joao.silva@example.com', '123456789', '12345678901'),
(2, 'Maria Oliveira', 'maria.oliveira@example.com', '987654321', '23456789012'),
(3, 'Carlos Santos', 'carlos.santos@example.com', '456123789', '34567890123');

INSERT INTO salas (id, nome, departamento, ativa) VALUES
(1, 'Sala A', 'TI', true),
(2, 'Sala B', 'Financeiro', true),
(3, 'Sala C', 'RH', false);

-- Reservas de teste
INSERT INTO reservas (id, usuario_id, sala_id, data_hora, status) VALUES
(1, 1, 1, '2024-12-01 14:00:00', 'ATIVA'),
(2, 2, 2, '2024-12-02 09:00:00', 'ATIVA'),
(3, 3, 1, '2024-12-03 11:00:00', 'CANCELADA');
