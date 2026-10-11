package ensino.emi.api.controller;

import ensino.emi.api.domain.trilha.Trilha;
import ensino.emi.api.domain.trilha.TrilhaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trilhas")
@CrossOrigin(origins = "*")
public class TrilhaController {

    private final TrilhaRepository trilhaRepository;

    public TrilhaController(TrilhaRepository trilhaRepository) {
        this.trilhaRepository = trilhaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Trilha>> listarTrilhas() {
        return ResponseEntity.ok(trilhaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Trilha> cadastrarTrilha(@RequestBody Trilha trilha) {
        return ResponseEntity.ok(trilhaRepository.save(trilha));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTrilha(@PathVariable Long id) {
        if (!trilhaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        trilhaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}