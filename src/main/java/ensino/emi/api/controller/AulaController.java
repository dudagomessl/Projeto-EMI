package ensino.emi.api.controller;

import ensino.emi.api.domain.aula.AulaService;
import ensino.emi.api.domain.aula.AulaService.ResultadoConclusaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aulas")
@CrossOrigin(origins = "*")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping("/{idAula}/concluir")
    public ResponseEntity<ResultadoConclusaoDTO> concluirAula(
            @PathVariable Long idAula,
            @RequestParam Long idUsuario) {

        ResultadoConclusaoDTO resultado = aulaService.concluirAula(idUsuario, idAula);
        return ResponseEntity.ok(resultado);
    }
}