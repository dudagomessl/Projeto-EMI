package ensino.emi.api.controller;

import ensino.emi.api.domain.modulo.ItemProgressoDTO;
import ensino.emi.api.domain.modulo.ModuloDTO;
import ensino.emi.api.domain.modulo.ModuloResponseDTO;
import ensino.emi.api.domain.modulo.ModuloService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/modulos")
public class ModuloController {

    private final ModuloService moduloService;

    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    /** GET /modulos?idTrilha=1 filtra pela trilha; sem parâmetro lista todos. */
    @GetMapping
    public ResponseEntity<List<ModuloResponseDTO>> listar(@RequestParam(required = false) Long idTrilha) {
        return ResponseEntity.ok(moduloService.listar(idTrilha));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuloResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(moduloService.buscar(id));
    }

    /** Linha do módulo (aulas + desafios) com concluído/liberado para o usuário. */
    @GetMapping("/{id}/progresso")
    public ResponseEntity<List<ItemProgressoDTO>> progresso(@PathVariable Long id,
                                                            @RequestParam Long idUsuario) {   // TEMPORÁRIO: virá do token
        return ResponseEntity.ok(moduloService.progresso(id, idUsuario));
    }

    @PostMapping
    public ResponseEntity<ModuloResponseDTO> cadastrar(@RequestBody @Valid ModuloDTO dados,
                                                       UriComponentsBuilder uriBuilder) {
        ModuloResponseDTO modulo = moduloService.cadastrar(dados);
        URI uri = uriBuilder.path("/modulos/{id}").buildAndExpand(modulo.idModulo()).toUri();
        return ResponseEntity.created(uri).body(modulo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuloResponseDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody @Valid ModuloDTO dados) {
        return ResponseEntity.ok(moduloService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        moduloService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
