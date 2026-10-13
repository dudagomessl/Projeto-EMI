package ensino.emi.api.domain.modulo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ModuloDTO(
        @NotBlank @Size(max = 100) String nomeModulo,
        @NotBlank @Size(max = 2000) String descricao,
        @NotNull Long idTrilha
) {
}
