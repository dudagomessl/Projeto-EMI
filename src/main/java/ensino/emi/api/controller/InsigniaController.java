package ensino.emi.api.controller;

import ensino.emi.api.domain.insignia.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insignias")
@CrossOrigin(origins = "*")
public class InsigniaController {

    private final InsigniaRepository insigniaRepository;

    public InsigniaController(InsigniaRepository insigniaRepository) {
        this.insigniaRepository = insigniaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Insignia>> listarInsignias() {
        return ResponseEntity.ok(insigniaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Insignia> cadastrarInsignia(@RequestBody InsigniaDTO dados) {
        Insignia insignia = new Insignia();
        insignia.setNomeInsignia(dados.nomeInsignia());
        insignia.setDescricao(dados.descricao());
        insignia.setIconeUrl(dados.iconeUrl());
        insignia.setRaridade(dados.raridade()); // <-- Essencial para não ir nulo!

        Insignia novaInsignia = insigniaRepository.save(insignia);
        return ResponseEntity.ok(novaInsignia);
    }
}