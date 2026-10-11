package ensino.emi.api.controller;

import ensino.emi.api.domain.aula.Aula;
import ensino.emi.api.domain.aula.AulaRepository;
import ensino.emi.api.domain.conteudo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conteudos")
@CrossOrigin(origins = "*")
public class ConteudoController {

    private final ConteudoRepository conteudoRepository;
    private final AulaRepository aulaRepository;

    public ConteudoController(ConteudoRepository conteudoRepository, AulaRepository aulaRepository) {
        this.conteudoRepository = conteudoRepository;
        this.aulaRepository = aulaRepository;
    }

    @GetMapping("/aula/{idAula}")
    public ResponseEntity<List<Conteudo>> listarConteudosPorAula(@PathVariable Long idAula) {
        return ResponseEntity.ok(conteudoRepository.findByAulaIdAula(idAula));
    }

    @PostMapping
    public ResponseEntity<Conteudo> cadastrarConteudo(@RequestBody ConteudoDTO dados) {
        Aula aula = aulaRepository.findById(dados.idAula())
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        Conteudo conteudo = new Conteudo();
        conteudo.setAula(aula);
        conteudo.setTituloSecao(dados.tituloSecao());
        conteudo.setCorpoTexto(dados.corpoTexto());
        conteudo.setOrdem(dados.ordem());

        return ResponseEntity.ok(conteudoRepository.save(conteudo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConteudo(@PathVariable Long id) {
        if (!conteudoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        conteudoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}