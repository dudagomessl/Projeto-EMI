package ensino.emi.api.domain.aula;

/** Aula sem o texto completo (listagens). */
public record AulaResumoDTO(
        Long idAula,
        Long idModulo,
        String nomeAula,
        String previaDoConteudo,
        Integer ordem
) {
    public static AulaResumoDTO de(Aula a) {
        return new AulaResumoDTO(a.getIdAula(), a.getModulo().getIdModulo(), a.getNomeAula(),
                a.getPreviaDoConteudo(), a.getOrdem());
    }
}
