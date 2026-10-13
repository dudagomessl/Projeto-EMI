package ensino.emi.api.controller;

import ensino.emi.api.domain.aula.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    /** GET /aulas?idModulo=1 filtra pelo módulo (sem o texto completo). */
    @GetMapping
    public ResponseEntity<List<AulaResumoDTO>> listar(@RequestParam(required = false) Long idModulo) {
        return ResponseEntity.ok(aulaService.listar(idModulo));
    }

    /** Aula completa: texto + vídeo. */
    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(aulaService.buscar(id));
    }

    @PostMapping
    public ResponseEntity<AulaResponseDTO> cadastrar(@RequestBody @Valid AulaDTO dados,
                                                     UriComponentsBuilder uriBuilder) {
        AulaResponseDTO aula = aulaService.cadastrar(dados);
        URI uri = uriBuilder.path("/aulas/{id}").buildAndExpand(aula.idAula()).toUri();
        return ResponseEntity.created(uri).body(aula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> atualizar(@PathVariable Long id,
                                                     @RequestBody @Valid AulaDTO dados) {
        return ResponseEntity.ok(aulaService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        aulaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // TEMPORÁRIO: idUsuario deve vir do token quando a autenticação existir
    @PostMapping("/{idAula}/concluir")
    public ResponseEntity<ResultadoConclusaoDTO> concluir(@PathVariable Long idAula,
                                                          @RequestParam Long idUsuario) {
        return ResponseEntity.ok(aulaService.concluirAula(idUsuario, idAula));
    }
}
