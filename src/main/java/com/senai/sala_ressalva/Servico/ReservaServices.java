package com.senai.sala_ressalva.Servico;

import com.senai.sala_ressalva.DTOs.ReservaDTO;
import com.senai.sala_ressalva.Entidade.Reserva;
import com.senai.sala_ressalva.Entidade.Sala;
import com.senai.sala_ressalva.Repositorio.ReservaRepositorio;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServices {

    private final ReservaRepositorio reservaRepository;
    private final SalaService salaService; // Dependência SalaService

    // Construtor único para injeção de dependências
    public ReservaServices(ReservaRepositorio reservaRepository, SalaService salaService) {
        this.reservaRepository = reservaRepository;
        this.salaService = salaService;
    }

    public ReservaDTO salvarReserva(Reserva reserva) {
        if (isReservaValida(reserva)) {
            throw new IllegalArgumentException("Reserva inválida");
        }
        if (!reserva.getSala().getAtiva()) {
            throw new IllegalArgumentException("Sala inativa. Não é possível fazer uma reserva.");
        }

        reserva.setStatus(true); // Define o status inicial como CONFIRMADA
        Reserva reservaSalva = reservaRepository.save(reserva);
        return convertToDTO(reservaSalva);
    }

    public ReservaDTO editarReserva(Long id, LocalDateTime novaDataHora, Long novaSalaId) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);

        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }

        Sala novaSala = salaService.buscarPorId(novaSalaId);
        if (novaSala == null) {
            throw new IllegalArgumentException("Sala não encontrada.");
        }

        if (novaDataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A reserva não pode ser alterada para uma data passada.");
        }

        reserva.setDataHora(novaDataHora);
        reserva.setSala(novaSala);

        Reserva reservaAtualizada = reservaRepository.save(reserva);
        return convertToDTO(reservaAtualizada);
    }

    public boolean cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva != null && reserva.getDataHora().isAfter(LocalDateTime.now())) {
            reserva.setStatus(false); // Atualiza o status para CANCELADA
            reservaRepository.save(reserva);
            return true;
        }
        return false;
    }

    public List<ReservaDTO> listarReservas() {
        return reservaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReservaDTO buscarPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        return reserva != null ? convertToDTO(reserva) : null;
    }

    private boolean isReservaValida(Reserva reserva) {
        LocalDateTime agora = LocalDateTime.now();

        // Verifica se a data não está no passado e não ultrapassa 30 dias no futuro
        if (reserva.getDataHora().isBefore(agora)) return true;
        if (ChronoUnit.DAYS.between(agora, reserva.getDataHora()) > 30) return true;

        // Verifica se já existe uma reserva para a mesma sala no mesmo horário
        List<Reserva> reservasExistentes = reservaRepository.findBySalaIdAndDataHora(
                reserva.getSala().getId(), reserva.getDataHora());
        return !reservasExistentes.isEmpty(); // Retorna true se não houver conflito
    }

    private ReservaDTO convertToDTO(Reserva reserva) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String timestamp = LocalDateTime.now().format(formatter);
        return new ReservaDTO(
                reserva.getId(),
                reserva.getUsuario().getId(),
                reserva.getSala().getId(),
                reserva.getDataHora(),
                reserva.getStatus(),
                timestamp
        );
    }
}


