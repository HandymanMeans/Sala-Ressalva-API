package com.senai.sala_ressalva.Servico;

import com.senai.sala_ressalva.Entidade.Usuario;
import com.senai.sala_ressalva.Repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepositorio usuarioRepository;

    public UsuarioService(UsuarioRepositorio usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario editar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setFone(usuarioAtualizado.getFone());
            usuario.setCpf(usuarioAtualizado.getCpf());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public boolean excluir(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
