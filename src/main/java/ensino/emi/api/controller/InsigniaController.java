package ensino.emi.api.controller;

import ensino.emi.api.domain.insignia.InsigniaDTO;
import ensino.emi.api.domain.insignia.InsigniaResponseDTO;
import ensino.emi.api.domain.insignia.InsigniaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/insignias")
public class InsigniaController {

    private final InsigniaService insigniaService;

    public InsigniaController(InsigniaService insigniaService) {
        this.insigniaService = insigniaService;
    }

    @GetMapping
    public ResponseEntity<List<InsigniaResponseDTO>> listar() {
        return ResponseEntity.ok(insigniaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsigniaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(insigniaService.buscar(id));
    }

    @PostMapping
    public ResponseEntity<InsigniaResponseDTO> cadastrar(@RequestBody @Valid InsigniaDTO dados,
                                                         UriComponentsBuilder uriBuilder) {
        InsigniaResponseDTO insignia = insigniaService.cadastrar(dados);
        URI uri = uriBuilder.path("/insignias/{id}").buildAndExpand(insignia.idInsignia()).toUri();
        return ResponseEntity.created(uri).body(insignia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsigniaResponseDTO> atualizar(@PathVariable Long id,
                                                         @RequestBody @Valid InsigniaDTO dados) {
        return ResponseEntity.ok(insigniaService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        insigniaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
