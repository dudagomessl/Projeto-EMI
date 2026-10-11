package ensino.emi.api.controller;

import ensino.emi.api.domain.modulo.Modulo;
import ensino.emi.api.domain.modulo.ModuloRepository;
import ensino.emi.api.domain.trilha.Trilha;
import ensino.emi.api.domain.trilha.TrilhaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modulos")
@CrossOrigin(origins = "*")
public class ModuloController {

    private final ModuloRepository moduloRepository;
    private final TrilhaRepository trilhaRepository;

    public ModuloController(ModuloRepository moduloRepository, TrilhaRepository trilhaRepository) {
        this.moduloRepository = moduloRepository;
        this.trilhaRepository = trilhaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Modulo>> listarModulos() {
        return ResponseEntity.ok(moduloRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Modulo> cadastrarModulo(@RequestBody ModuloDTO dados) {
        Trilha trilha = trilhaRepository.findById(dados.idTrilha())
                .orElseThrow(() -> new RuntimeException("Trilha não encontrada"));

        Modulo modulo = new Modulo();
        modulo.setNomeModulo(dados.nomeModulo());
        modulo.setDescricao(dados.descricao());
        modulo.setTrilha(trilha);

        return ResponseEntity.ok(moduloRepository.save(modulo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarModulo(@PathVariable Long id) {
        if (!moduloRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        moduloRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public record ModuloDTO(String nomeModulo, String descricao, Long idTrilha) {
    }
}