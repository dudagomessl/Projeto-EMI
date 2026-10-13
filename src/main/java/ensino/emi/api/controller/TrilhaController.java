package ensino.emi.api.controller;

import ensino.emi.api.domain.trilha.TrilhaDTO;
import ensino.emi.api.domain.trilha.TrilhaResponseDTO;
import ensino.emi.api.domain.trilha.TrilhaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/trilhas")
public class TrilhaController {

    private final TrilhaService trilhaService;

    public TrilhaController(TrilhaService trilhaService) {
        this.trilhaService = trilhaService;
    }

    @GetMapping
    public ResponseEntity<List<TrilhaResponseDTO>> listar() {
        return ResponseEntity.ok(trilhaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrilhaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(trilhaService.buscar(id));
    }

    @PostMapping
    public ResponseEntity<TrilhaResponseDTO> cadastrar(@RequestBody @Valid TrilhaDTO dados,
                                                       UriComponentsBuilder uriBuilder) {
        TrilhaResponseDTO trilha = trilhaService.cadastrar(dados);
        URI uri = uriBuilder.path("/trilhas/{id}").buildAndExpand(trilha.idTrilha()).toUri();
        return ResponseEntity.created(uri).body(trilha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrilhaResponseDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody @Valid TrilhaDTO dados) {
        return ResponseEntity.ok(trilhaService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        trilhaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
