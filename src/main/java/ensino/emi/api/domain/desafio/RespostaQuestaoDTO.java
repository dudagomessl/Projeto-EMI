package ensino.emi.api.domain.desafio;

import jakarta.validation.constraints.NotNull;

public record RespostaQuestaoDTO(
        @NotNull Long idQuestao,
        @NotNull Alternativa resposta
) {
}
