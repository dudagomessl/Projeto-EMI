package ensino.emi.api.controller;

import ensino.emi.api.domain.usuario.LoginDTO;
import ensino.emi.api.domain.usuario.UsuarioResponseDTO;
import ensino.emi.api.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Login PROVISÓRIO (sem token). Na etapa de segurança vira JWT. */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(@RequestBody @Valid LoginDTO dados) {
        return ResponseEntity.ok(usuarioService.autenticar(dados));
    }
}
