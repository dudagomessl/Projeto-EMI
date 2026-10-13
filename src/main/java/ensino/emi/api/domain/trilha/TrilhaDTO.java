package ensino.emi.api.domain.trilha;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrilhaDTO(
        @NotBlank @Size(max = 100) String nomeTrilha
) {
}
