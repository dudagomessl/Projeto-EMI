package ensino.emi.api.domain.desafio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

/** Cadastro de um desafio já com suas questões. */
public record DesafioDTO(
        @NotNull Long idModulo,
        @NotBlank @Size(max = 100) String nomeDesafio,
        @NotNull @Min(0) @Max(100) Integer notaDeCorte,
        @NotNull @Min(0) Integer recompensaXp,
        @NotNull @Min(1) Integer ordem,
        @NotEmpty @Valid List<QuestaoDTO> questoes
) {
}
