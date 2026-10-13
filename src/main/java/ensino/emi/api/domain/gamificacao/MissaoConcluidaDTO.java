package ensino.emi.api.domain.gamificacao;

import ensino.emi.api.domain.insignia.InsigniaResponseDTO;

/** Avisa o front que uma missão acabou de ser cumprida (para exibir o "parabéns"). */
public record MissaoConcluidaDTO(
        Long idMissao,
        String titulo,
        int xpGanho,
        InsigniaResponseDTO insigniaConquistada
) {
}
