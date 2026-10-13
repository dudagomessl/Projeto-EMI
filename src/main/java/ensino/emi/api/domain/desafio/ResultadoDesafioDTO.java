package ensino.emi.api.domain.desafio;

import ensino.emi.api.domain.gamificacao.MissaoConcluidaDTO;

import java.util.List;

public record ResultadoDesafioDTO(
        int nota,
        boolean aprovado,
        int acertos,
        int totalQuestoes,
        List<Long> questoesErradas,
        int xpGanho,
        int xpTotal,
        int xpAtualNivel,
        int nivelAtual,
        List<MissaoConcluidaDTO> missoesConcluidas
) {
}
