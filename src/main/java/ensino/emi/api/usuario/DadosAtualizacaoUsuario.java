package ensino.emi.api.usuario;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long id,
        String nome,
        Integer nivelDaConta,
        Double notaMedia,
        MateriaFavorita materiaFavorita) {
}
