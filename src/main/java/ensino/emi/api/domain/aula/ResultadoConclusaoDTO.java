package ensino.emi.api.domain.aula;

import ensino.emi.api.domain.gamificacao.MissaoConcluidaDTO;

import java.util.List;

public record ResultadoConclusaoDTO(
        String mensagem,
        int xpGanho,
        int xpTotal,
        int xpAtualNivel,
        int nivelAtual,
        List<MissaoConcluidaDTO> missoesConcluidas
) {
}
