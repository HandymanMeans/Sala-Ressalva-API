

DELIMITER //
use sala_ressalva
CREATE TRIGGER valida_data_reserva
BEFORE INSERT ON reserva
FOR EACH ROW
BEGIN
    -- Verifica se a data da reserva é anterior à data do pedido
    IF NEW.data_reserva < NEW.data_pedido THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A data da reserva não pode ser anterior à data do pedido.';
    END IF;

    -- Verifica se a data da reserva é maior que 30 dias no futuro
    IF NEW.data_reserva > NOW() + INTERVAL 30 DAY THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A reserva não pode ser feita com mais de 30 dias de antecedência.';
    END IF;

    -- Verifica se a data da reserva é no passado
    IF NEW.data_reserva < NOW() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A data da reserva não pode ser no passado.';
    END IF;    
END;
DELIMITER //

CREATE TRIGGER valida_reserva_sala
BEFORE INSERT ON reserva
FOR EACH ROW
BEGIN
    DECLARE sala_reservada INT;
    SELECT COUNT(*) INTO sala_reservada
    FROM reserva
    WHERE sala_id = NEW.sala_id
      AND DATE(NEW.data_reserva) = DATE(data_reserva)
      AND NEW.data_reserva = data_reserva
      AND cancelada = FALSE;
    IF sala_reservada > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A sala já está reservada para esse horário.';
    END IF;
END //

DELIMITER ;


//

DELIMITER ;
DELIMITER //

CREATE TRIGGER valida_reserva_usuario
BEFORE INSERT ON reserva
FOR EACH ROW
BEGIN
    DECLARE reserva_existente INT;
    SELECT COUNT(*) INTO reserva_existente
    FROM reserva
    WHERE usuario_id = NEW.usuario_id
      AND DATE(NEW.data_reserva) = DATE(data_reserva)
      AND is_cancelada = FALSE;

    IF reserva_existente > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'O usuário já possui uma reserva para esse dia.';
    END IF;
END //

DELIMITER ;
DELIMITER //

CREATE TRIGGER valida_edicao_reserva
BEFORE UPDATE ON reserva
FOR EACH ROW
BEGIN
    -- Verifica se a reserva já ocorreu
    IF OLD.data_reserva < NOW() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Não é possível editar uma reserva que já ocorreu.';
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE TRIGGER valida_cancelamento_reserva
BEFORE DELETE ON reservas
FOR EACH ROW
BEGIN
    -- Verifica se a reserva já ocorreu
    IF OLD.data_reserva < NOW() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Não é possível cancelar uma reserva que já ocorreu.';
    END IF;
END //

DELIMITER ;




