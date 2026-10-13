package ensino.emi.api.domain.usuario;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioDTO(
        @NotBlank @Size(max = 100) String nomeUsuario,
        @NotBlank @Email @Size(max = 150) String email,
        @NotBlank @Size(min = 6, max = 72) String senha,   // 72 = limite do BCrypt
        @NotNull @Past LocalDate dataNascimento
) {
}
