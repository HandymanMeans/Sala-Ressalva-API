package com.senai.sala_ressalva.Controlador;

import com.senai.sala_ressalva.DTOs.ReservaDTO;
import com.senai.sala_ressalva.Entidade.Reserva;
import com.senai.sala_ressalva.Entidade.Sala;
import com.senai.sala_ressalva.Entidade.Usuario;
import com.senai.sala_ressalva.Servico.ReservaServices;
import com.senai.sala_ressalva.Servico.SalaService;
import com.senai.sala_ressalva.Servico.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaServices reservaService;
    private final UsuarioService usuarioService;
    private final SalaService salaService;

    public ReservaController(ReservaServices reservaService, UsuarioService usuarioService, SalaService salaService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.salaService = salaService;
    }

    // Método auxiliar para gerar timestamp no formato ISO
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    // Método auxiliar para criar resposta padronizada para erros
    private ResponseEntity<Map<String, Object>> createErrorResponse(String message, String path, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        errorResponse.put("path", path);
        errorResponse.put("status", status.value());
        errorResponse.put("timestamp", getCurrentTimestamp());
        return ResponseEntity.status(status).body(errorResponse);
    }

    // Método auxiliar para criar resposta de sucesso com dados da reserva
    private Map<String, Object> convertReservaToResponse(ReservaDTO reservaDTO) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", reservaDTO.getId());
        response.put("dataHora", reservaDTO.getDataHora());
        response.put("usuario", reservaDTO.getUsuarioId());
        response.put("sala", reservaDTO.getSalaId());
        response.put("status", reservaDTO.isStatus());
        response.put("timestamp", getCurrentTimestamp());
        return response;
    }

    // 1. Criar nova reserva
    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioService.buscarPorId(reservaDTO.getUsuarioId()));
            Optional<Sala> salaOpt = Optional.ofNullable(salaService.buscarPorId(reservaDTO.getSalaId()));

            if (usuarioOpt.isEmpty() || salaOpt.isEmpty()) {
                return createErrorResponse("Usuário ou Sala não encontrados", "/reservas", HttpStatus.BAD_REQUEST);
            }

            Reserva reserva = new Reserva(usuarioOpt.get(), salaOpt.get(), reservaDTO.getDataHora());
            ReservaDTO novaReservaDTO = reservaService.salvarReserva(reserva);

            return ResponseEntity.status(HttpStatus.CREATED).body(convertReservaToResponse(novaReservaDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return createErrorResponse("Erro ao criar reserva: " + e.getMessage(), "/reservas", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. Listar todas as reservas
    @GetMapping
    public ResponseEntity<?> listarReservas() {
        List<ReservaDTO> reservas = reservaService.listarReservas();
        List<Map<String, Object>> reservasResponse = reservas.stream()
                .map(this::convertReservaToResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("reservas", reservasResponse);
        response.put("timestamp", getCurrentTimestamp());

        return ResponseEntity.ok(response);
    }

    // 3. Buscar reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarReservaPorId(@PathVariable Long id) {
        ReservaDTO reservaDTO = reservaService.buscarPorId(id);
        if (reservaDTO == null) {
            return createErrorResponse("Reserva não encontrada", "/reservas/" + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(convertReservaToResponse(reservaDTO));
    }

    // 4. Editar uma reserva existente
    @PutMapping("/{id}")
    public ResponseEntity<?> editarReserva(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            // Validar os parâmetros do payload
            if (!payload.containsKey("novaDataHora") || !payload.containsKey("novaSalaId")) {
                return createErrorResponse(
                        "Parâmetros obrigatórios ausentes: 'novaDataHora' ou 'novaSalaId'",
                        "/reservas/" + id,
                        HttpStatus.BAD_REQUEST
                );
            }

            String novaDataHoraStr = (String) payload.get("novaDataHora");
            Long novaSalaId = ((Number) payload.get("novaSalaId")).longValue();

            if (novaDataHoraStr == null) {
                return createErrorResponse(
                        "Valores nulos para 'novaDataHora' ou 'novaSalaId'",
                        "/reservas/" + id,
                        HttpStatus.BAD_REQUEST
                );
            }

            LocalDateTime novaDataHora = LocalDateTime.parse(novaDataHoraStr);
            ReservaDTO reservaEditada = reservaService.editarReserva(id, novaDataHora, novaSalaId);

            if (reservaEditada == null) {
                return createErrorResponse(
                        "Reserva não encontrada ou dados inválidos",
                        "/reservas/" + id,
                        HttpStatus.BAD_REQUEST
                );
            }

            return ResponseEntity.ok(convertReservaToResponse(reservaEditada));
        } catch (Exception e) {
            e.printStackTrace();
            return createErrorResponse(
                    "Erro ao editar reserva: " + e.getMessage(),
                    "/reservas/" + id,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // 5. Cancelar uma reserva existente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        boolean cancelada = reservaService.cancelarReserva(id);
        if (!cancelada) {
            return createErrorResponse("Reserva não encontrada", "/reservas/" + id, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reserva cancelada com sucesso");
        response.put("timestamp", getCurrentTimestamp());

        return ResponseEntity.ok(response);
    }
}
