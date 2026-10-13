package ensino.emi.api.controller;

import ensino.emi.api.domain.missao.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    private final MissaoService missaoService;

    public MissaoController(MissaoService missaoService) {
        this.missaoService = missaoService;
    }

    @GetMapping
    public ResponseEntity<List<MissaoResponseDTO>> listar() {
        return ResponseEntity.ok(missaoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(missaoService.buscar(id));
    }

    /** Missões com o progresso do usuário (ex.: 3 de 5). */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<MissaoProgressoDTO>> listarDoUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(missaoService.listarDoUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<MissaoResponseDTO> cadastrar(@RequestBody @Valid MissaoDTO dados,
                                                       UriComponentsBuilder uriBuilder) {
        MissaoResponseDTO missao = missaoService.cadastrar(dados);
        URI uri = uriBuilder.path("/missoes/{id}").buildAndExpand(missao.idMissao()).toUri();
        return ResponseEntity.created(uri).body(missao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissaoResponseDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody @Valid MissaoDTO dados) {
        return ResponseEntity.ok(missaoService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        missaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
