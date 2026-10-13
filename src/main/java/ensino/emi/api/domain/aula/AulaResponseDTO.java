package ensino.emi.api.domain.aula;

/** Aula completa (tela de estudo). */
public record AulaResponseDTO(
        Long idAula,
        Long idModulo,
        String nomeAula,
        String previaDoConteudo,
        String texto,
        String videoUrl,
        Integer ordem
) {
    public static AulaResponseDTO de(Aula a) {
        return new AulaResponseDTO(a.getIdAula(), a.getModulo().getIdModulo(), a.getNomeAula(),
                a.getPreviaDoConteudo(), a.getTexto(), a.getVideoUrl(), a.getOrdem());
    }
}
