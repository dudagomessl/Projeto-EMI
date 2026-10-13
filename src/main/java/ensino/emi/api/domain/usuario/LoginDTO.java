package ensino.emi.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank String email,
        @NotBlank String senha
) {
}
