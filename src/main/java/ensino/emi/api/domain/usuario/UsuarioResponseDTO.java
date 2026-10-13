package ensino.emi.api.domain.usuario;

/** Perfil do próprio usuário. Nunca contém a senha. */
public record UsuarioResponseDTO(
        Long idUsuario,
        String nomeUsuario,
        String email,
        int experiencia,
        int nivelAtual,
        int xpNoNivelAtual,
        int xpPorNivel
) {
    public static UsuarioResponseDTO de(Usuario u) {
        return new UsuarioResponseDTO(
                u.getIdUsuario(),
                u.getNomeUsuario(),
                u.getEmail(),
                u.getExperiencia(),
                u.getNivelAtual(),
                u.getXpNoNivelAtual(),
                Usuario.XP_POR_NIVEL);
    }
}
