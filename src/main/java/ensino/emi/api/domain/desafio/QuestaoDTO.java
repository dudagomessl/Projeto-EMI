package ensino.emi.api.domain.desafio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuestaoDTO(
        @NotBlank @Size(max = 2000) String enunciado,
        @NotBlank @Size(max = 500) String alternativaA,
        @NotBlank @Size(max = 500) String alternativaB,
        @NotBlank @Size(max = 500) String alternativaC,
        @NotBlank @Size(max = 500) String alternativaD,
        @NotNull Alternativa respostaCorreta
) {
}
