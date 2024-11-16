package com.senai.sala_ressalva.DTOs;

import com.senai.sala_ressalva.Entidade.Usuario;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String timestamp;

    // Construtor para preencher o DTO a partir de um objeto Usuario
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.telefone = usuario.getFone();
        this.cpf = usuario.getCpf();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
}

