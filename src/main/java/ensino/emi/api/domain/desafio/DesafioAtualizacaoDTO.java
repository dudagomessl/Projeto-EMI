package ensino.emi.api.domain.desafio;

import jakarta.validation.constraints.*;

/** Atualiza só os dados do desafio. Para mudar as questões, apague e recadastre o desafio. */
public record DesafioAtualizacaoDTO(
        @NotBlank @Size(max = 100) String nomeDesafio,
        @NotNull @Min(0) @Max(100) Integer notaDeCorte,
        @NotNull @Min(0) Integer recompensaXp,
        @NotNull @Min(1) Integer ordem
) {
}
