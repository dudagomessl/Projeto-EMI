package ensino.emi.api.domain.desafio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RespostaDesafioDTO(
        @NotNull Long idUsuario,   // TEMPORÁRIO: virá do token JWT
        @NotEmpty @Valid List<RespostaQuestaoDTO> respostas
) {
}
