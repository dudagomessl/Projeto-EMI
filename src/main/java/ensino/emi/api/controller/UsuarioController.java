package ensino.emi.api.controller;

import ensino.emi.api.domain.insignia.InsigniaResponseDTO;
import ensino.emi.api.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioDTO dados,
                                                        UriComponentsBuilder uriBuilder) {
        UsuarioResponseDTO usuario = usuarioService.cadastrar(dados);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.idUsuario()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    /** Lista pública ordenada por XP (ranking). */
    @GetMapping
    public ResponseEntity<List<UsuarioResumoDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid UsuarioAtualizacaoDTO dados) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/insignias")
    public ResponseEntity<List<InsigniaResponseDTO>> listarInsignias(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.listarInsignias(id));
    }
}
