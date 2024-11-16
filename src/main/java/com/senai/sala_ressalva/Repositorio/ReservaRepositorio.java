package com.senai.sala_ressalva.Repositorio;

import com.senai.sala_ressalva.Entidade.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
    List<Reserva> findBySalaIdAndDataHora(Long salaId, LocalDateTime dataHora);
}





