package com.senai.sala_ressalva.Servico;

import com.senai.sala_ressalva.Entidade.Sala;
import com.senai.sala_ressalva.Repositorio.SalaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepositorio salaRepository;

    public SalaService(SalaRepositorio salaRepository) {
        this.salaRepository = salaRepository;
    }

    public Sala salvar(Sala sala) {
        return salaRepository.save(sala);
    }

    public List<Sala> listarTodas() {
        return salaRepository.findAll();
    }

    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id).orElse(null);
    }

    public Sala editar(Long id, Sala salaAtualizada) {
        Sala sala = buscarPorId(id);
        if (sala != null) {
            sala.setNome(salaAtualizada.getNome());
            sala.setDepartamento(salaAtualizada.getDepartamento());
            return salaRepository.save(sala);
        }
        return null;
    }

    public boolean excluir(Long id) {
        if (salaRepository.existsById(id)) {
            salaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

