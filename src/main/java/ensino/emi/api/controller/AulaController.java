package ensino.emi.api.controller;

import ensino.emi.api.domain.aula.Aula;
import ensino.emi.api.domain.aula.AulaDTO;
import ensino.emi.api.domain.aula.AulaRepository;
import ensino.emi.api.domain.aula.AulaService;
import ensino.emi.api.domain.aula.AulaService.ResultadoConclusaoDTO;
import ensino.emi.api.domain.modulo.Modulo;
import ensino.emi.api.domain.modulo.ModuloRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
@CrossOrigin(origins = "*")
public class AulaController {

    private final AulaService aulaService;
    private final AulaRepository aulaRepository;
    private final ModuloRepository moduloRepository;

    public AulaController(AulaService aulaService, AulaRepository aulaRepository, ModuloRepository moduloRepository) {
        this.aulaService = aulaService;
        this.aulaRepository = aulaRepository;
        this.moduloRepository = moduloRepository;
    }

    @GetMapping
    public ResponseEntity<List<Aula>> listarAulas() {
        return ResponseEntity.ok(aulaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Aula> cadastrarAula(@RequestBody AulaDTO dados) {
        Modulo modulo = moduloRepository.findById(dados.idModulo())
                .orElseThrow(() -> new RuntimeException("Módulo não encontrado"));

        Aula aula = new Aula();
        aula.setNomeAula(dados.nomeAula());
        aula.setPreviaDoConteudo(dados.previaDoConteudo());
        aula.setStatusDeConclusao(0);
        aula.setModulo(modulo);

        return ResponseEntity.ok(aulaRepository.save(aula));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAula(@PathVariable Long id) {
        if (!aulaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        aulaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idAula}/concluir")
    public ResponseEntity<ResultadoConclusaoDTO> concluirAula(
            @PathVariable Long idAula,
            @RequestParam Long idUsuario) {

        ResultadoConclusaoDTO resultado = aulaService.concluirAula(idUsuario, idAula);
        return ResponseEntity.ok(resultado);
    }
}