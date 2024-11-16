package com.senai.sala_ressalva.Entidade;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String departamento;

    @Column(nullable = false)
    private boolean ativa = true;


    // Getter para o campo status
    public Boolean getAtiva() {
        return ativa;
    }

    // Setter para o campo status
    public void setAtiva(Boolean ativo) {
        this.ativa = ativo;
    }
}
