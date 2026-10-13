package ensino.emi.api.domain.aula;

import jakarta.validation.constraints.*;

public record AulaDTO(
        @NotNull Long idModulo,
        @NotBlank @Size(max = 100) String nomeAula,
        @NotBlank @Size(max = 500) String previaDoConteudo,
        @NotBlank @Size(max = 60000) String texto,
        @Size(max = 255) String videoUrl,
        @NotNull @Min(1) Integer ordem
) {
}
