package com.senai.sala_ressalva.Repositorio;

import com.senai.sala_ressalva.Entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
}
