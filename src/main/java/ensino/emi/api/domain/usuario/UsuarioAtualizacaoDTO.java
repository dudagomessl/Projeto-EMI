package ensino.emi.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioAtualizacaoDTO(
        @NotBlank @Size(max = 100) String nomeUsuario,
        @NotNull @Past LocalDate dataNascimento
) {
}
