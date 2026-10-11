package ensino.emi.api.domain.usuario;

import java.time.LocalDate;

public record UsuarioDTO(
        String nomeUsuario,
        String email,
        String senha,
        LocalDate dataNascimento
) {
}