package ensino.emi.api.domain.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnviarMensagemDTO(
        @NotNull Long idUsuario,   // TEMPORÁRIO: virá do token JWT
        @NotBlank @Size(max = 2000) String mensagem
) {
}
