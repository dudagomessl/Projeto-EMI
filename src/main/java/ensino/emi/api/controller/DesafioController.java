package ensino.emi.api.controller;

import ensino.emi.api.domain.desafio.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/desafios")
public class DesafioController {

    private final DesafioService desafioService;

    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    /** GET /desafios?idModulo=1 filtra pelo módulo. */
    @GetMapping
    public ResponseEntity<List<DesafioResumoDTO>> listar(@RequestParam(required = false) Long idModulo) {
        return ResponseEntity.ok(desafioService.listar(idModulo));
    }

    /** Desafio com as questões (sem gabarito). */
    @GetMapping("/{id}")
    public ResponseEntity<DesafioResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(desafioService.buscar(id));
    }

    @PostMapping
    public ResponseEntity<DesafioResponseDTO> cadastrar(@RequestBody @Valid DesafioDTO dados,
                                                        UriComponentsBuilder uriBuilder) {
        DesafioResponseDTO desafio = desafioService.cadastrar(dados);
        URI uri = uriBuilder.path("/desafios/{id}").buildAndExpand(desafio.idDesafio()).toUri();
        return ResponseEntity.created(uri).body(desafio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DesafioResumoDTO> atualizar(@PathVariable Long id,
                                                      @RequestBody @Valid DesafioAtualizacaoDTO dados) {
        return ResponseEntity.ok(desafioService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        desafioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /** O aluno envia as alternativas escolhidas; o back-end corrige. */
    @PostMapping("/{id}/responder")
    public ResponseEntity<ResultadoDesafioDTO> responder(@PathVariable Long id,
                                                         @RequestBody @Valid RespostaDesafioDTO dados) {
        return ResponseEntity.ok(desafioService.responder(id, dados));
    }
}
