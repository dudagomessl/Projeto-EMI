package ensino.emi.api.domain.missao;

import jakarta.validation.constraints.*;

public record MissaoDTO(
        @NotBlank @Size(max = 100) String titulo,
        @NotBlank @Size(max = 1000) String descricao,
        @NotNull TipoMissao tipo,
        @NotNull @Min(1) Integer meta,
        Long idModulo,                 // opcional
        @NotNull @Min(0) Integer recompensaXp,
        Long idInsigniaRecompensa      // opcional
) {
}
