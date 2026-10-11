package ensino.emi.api.domain.usuario;

import java.time.LocalDate;

public record DadosListagemUsuario(
        Long idUsuario,
        String nomeUsuario,
        String email,
        LocalDate dataNascimento,
        Integer experiencia
) {
}