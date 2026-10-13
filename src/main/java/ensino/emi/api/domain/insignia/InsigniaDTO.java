package ensino.emi.api.domain.insignia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InsigniaDTO(
        @NotBlank @Size(max = 100) String nomeInsignia,
        @Size(max = 1000) String descricao,
        @Size(max = 255) String iconeUrl,
        @NotNull RaridadeInsignia raridade
) {
}
