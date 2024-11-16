
package com.senai.sala_ressalva.Controlador;

import com.senai.sala_ressalva.Entidade.Sala;
import com.senai.sala_ressalva.Servico.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    // Método auxiliar para gerar o timestamp no formato ISO
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    // Método auxiliar para converter uma Sala em um Map<String, Object> com o timestamp
    private Map<String, Object> convertSalaToMap(Sala sala) {
        Map<String, Object> salaMap = new HashMap<>();
        salaMap.put("id", sala.getId());
        salaMap.put("nome", sala.getNome());
        salaMap.put("departamento", sala.getDepartamento());
        salaMap.put("ativa", sala.getAtiva());
        salaMap.put("timestamp", getCurrentTimestamp());
        return salaMap;
    }

    // Método auxiliar para converter um ErrorResponse em Map<String, Object>
    private Map<String, Object> convertErrorToMap(String message, String path, int status) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("message", message);
        errorMap.put("path", path);
        errorMap.put("status", status);
        errorMap.put("timestamp", getCurrentTimestamp());
        return errorMap;
    }

    // Método POST para criar nova sala
    @PostMapping
    public ResponseEntity<Map<String, Object>> criarSala(@RequestBody Sala sala) {
        try {
            Sala novaSala = salaService.salvar(sala);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertSalaToMap(novaSala));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(convertErrorToMap("Erro ao criar sala: " + e.getMessage(), "/salas", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    // Método GET para listar todas as salas
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodasSalas() {
        List<Map<String, Object>> salas = salaService.listarTodas()
                .stream()
                .map(this::convertSalaToMap)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("salas", salas);
        response.put("timestamp", getCurrentTimestamp());

        return ResponseEntity.ok(response);
    }

    // Método GET para buscar uma sala por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarSalaPorId(@PathVariable Long id) {
        Sala sala = salaService.buscarPorId(id);
        return sala != null ? ResponseEntity.ok(convertSalaToMap(sala)) : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(convertErrorToMap("Sala não encontrada", "/salas/" + id, HttpStatus.NOT_FOUND.value()));
    }

    // Método PUT para editar uma sala existente
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editarSala(@PathVariable Long id, @RequestBody Sala salaAtualizada) {
        try {
            Sala salaEditada = salaService.editar(id, salaAtualizada);
            return salaEditada != null ? ResponseEntity.ok(convertSalaToMap(salaEditada)) : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(convertErrorToMap("Sala não encontrada ou dados inválidos", "/salas/" + id, HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(convertErrorToMap("Erro ao editar sala: " + e.getMessage(), "/salas/" + id, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    // Método DELETE para excluir uma sala
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> excluirSala(@PathVariable Long id) {
        boolean excluida = salaService.excluir(id);

        if (!excluida) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(convertErrorToMap("Sala não encontrada", "/salas/" + id, HttpStatus.NOT_FOUND.value()));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Sala excluída com sucesso.");
        response.put("timestamp", getCurrentTimestamp());

        return ResponseEntity.ok(response);
    }
}
