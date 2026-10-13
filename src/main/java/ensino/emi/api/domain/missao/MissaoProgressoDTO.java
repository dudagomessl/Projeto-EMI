package ensino.emi.api.domain.missao;

import ensino.emi.api.domain.insignia.InsigniaResponseDTO;

import java.time.LocalDateTime;

/** Missão com o progresso de um usuário (ex.: 3 de 5). */
public record MissaoProgressoDTO(
        Long idMissao,
        String titulo,
        String descricao,
        int progresso,
        int meta,
        boolean concluida,
        int recompensaXp,
        InsigniaResponseDTO insigniaRecompensa,
        LocalDateTime dataConquista
) {
    public static MissaoProgressoDTO de(Missao m, int progresso, boolean concluida, LocalDateTime dataConquista) {
        return new MissaoProgressoDTO(
                m.getIdMissao(), m.getTitulo(), m.getDescricao(), progresso, m.getMeta(), concluida,
                m.getRecompensaXp(),
                m.getInsigniaRecompensa() != null ? InsigniaResponseDTO.de(m.getInsigniaRecompensa()) : null,
                dataConquista);
    }
}
