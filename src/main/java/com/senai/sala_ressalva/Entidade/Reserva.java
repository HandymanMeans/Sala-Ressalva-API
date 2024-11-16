package com.senai.sala_ressalva.Entidade;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    private LocalDateTime dataHora;


    @Column(nullable = false)
    private Boolean status = true;;

    public Reserva(Usuario usuario, Sala sala, LocalDateTime dataHora) {
        this.usuario = usuario;
        this.sala = sala;
        this.dataHora = dataHora;
        this.status = true;
    }
}


