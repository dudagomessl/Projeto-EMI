package ensino.emi.api.domain.insignia;

public record InsigniaResponseDTO(
        Long idInsignia,
        String nomeInsignia,
        String descricao,
        String iconeUrl,
        RaridadeInsignia raridade
) {
    public static InsigniaResponseDTO de(Insignia i) {
        return new InsigniaResponseDTO(i.getIdInsignia(), i.getNomeInsignia(),
                i.getDescricao(), i.getIconeUrl(), i.getRaridade());
    }
}
