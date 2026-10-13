package ensino.emi.api.domain.desafio;

public record DesafioResumoDTO(
        Long idDesafio,
        Long idModulo,
        String nomeDesafio,
        Integer notaDeCorte,
        Integer recompensaXp,
        Integer ordem
) {
    public static DesafioResumoDTO de(Desafio d) {
        return new DesafioResumoDTO(d.getIdDesafio(), d.getModulo().getIdModulo(), d.getNomeDesafio(),
                d.getNotaDeCorte(), d.getRecompensaXp(), d.getOrdem());
    }
}
