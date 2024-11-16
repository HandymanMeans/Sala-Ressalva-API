package com.senai.sala_ressalva.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
    private String messsagem;
    private String path;
    private int status;

    public ErrorResponse(String messsagem, String path, int status) {
        this.messsagem = messsagem;
        this.path = path;
        this.status = status;
    }

    public ErrorResponse(ErrorResponse erroAoEditarSala) {
    }

}

