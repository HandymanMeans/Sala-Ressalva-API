package com.senai.sala_ressalva.DTOs;

import com.senai.sala_ressalva.Entidade.Reserva;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class ReservaDTO {

    private Long id;
    private Long usuarioId;
    private Long salaId;
    private LocalDateTime dataHora;
    @Getter
    @Setter
    private boolean status;
    private String timestamp;

    public ReservaDTO(Long id, Long usuarioId, Long salaId, LocalDateTime dataHora, boolean status, String timestamp) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.salaId = salaId;
        this.dataHora = dataHora;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ReservaDTO(Reserva reservaAtualizada) {

    }
}
