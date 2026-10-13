package ensino.emi.api.domain.missao;

import ensino.emi.api.domain.insignia.InsigniaResponseDTO;

public record MissaoResponseDTO(
        Long idMissao,
        String titulo,
        String descricao,
        TipoMissao tipo,
        Integer meta,
        Long idModulo,
        Integer recompensaXp,
        InsigniaResponseDTO insigniaRecompensa
) {
    public static MissaoResponseDTO de(Missao m) {
        return new MissaoResponseDTO(
                m.getIdMissao(), m.getTitulo(), m.getDescricao(), m.getTipo(), m.getMeta(),
                m.getModulo() != null ? m.getModulo().getIdModulo() : null,
                m.getRecompensaXp(),
                m.getInsigniaRecompensa() != null ? InsigniaResponseDTO.de(m.getInsigniaRecompensa()) : null);
    }
}
