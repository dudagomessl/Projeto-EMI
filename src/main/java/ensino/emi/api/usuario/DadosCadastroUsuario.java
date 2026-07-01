package ensino.emi.api.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroUsuario(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotNull
        int nivelDaConta,
        //@NotNull
        //double notaMedia,
        @NotNull
        MateriaFavorita materiaFavorita) {
}
