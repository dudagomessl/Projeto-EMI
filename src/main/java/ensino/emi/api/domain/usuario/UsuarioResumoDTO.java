package ensino.emi.api.domain.usuario;

/** Dados públicos (ranking/listagem): sem e-mail e sem data de nascimento. */
public record UsuarioResumoDTO(
        Long idUsuario,
        String nomeUsuario,
        int experiencia,
        int nivelAtual
) {
    public static UsuarioResumoDTO de(Usuario u) {
        return new UsuarioResumoDTO(u.getIdUsuario(), u.getNomeUsuario(), u.getExperiencia(), u.getNivelAtual());
    }
}
