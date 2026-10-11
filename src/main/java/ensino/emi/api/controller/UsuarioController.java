package ensino.emi.api.controller;

import ensino.emi.api.domain.insignia.Insignia;
import ensino.emi.api.domain.usuario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody UsuarioDTO dados) {
        Usuario novoUsuario = usuarioService.cadastrarUsuario(dados);
        return ResponseEntity.ok(novoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemUsuario>> listarUsuarios() {
        List<DadosListagemUsuario> usuarios = usuarioService.listar();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> detalhar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private UsuarioInsigniaRepository usuarioInsigniaRepository;

    @GetMapping("/{id}/insignias")
    public ResponseEntity<List<Insignia>> listarInsigniasDoUsuario(@PathVariable Long id) {
        List<Insignia> insignias = usuarioInsigniaRepository.findInsigniasPorUsuario(id);
        return ResponseEntity.ok(insignias);
    }

}