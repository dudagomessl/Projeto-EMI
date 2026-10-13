package ensino.emi.api.domain.desafio;

import java.util.List;

public record DesafioResponseDTO(
        Long idDesafio,
        Long idModulo,
        String nomeDesafio,
        Integer notaDeCorte,
        Integer recompensaXp,
        Integer ordem,
        List<QuestaoResponseDTO> questoes
) {
    public static DesafioResponseDTO de(Desafio d, List<Questao> questoes) {
        return new DesafioResponseDTO(d.getIdDesafio(), d.getModulo().getIdModulo(), d.getNomeDesafio(),
                d.getNotaDeCorte(), d.getRecompensaXp(), d.getOrdem(),
                questoes.stream().map(QuestaoResponseDTO::de).toList());
    }
}
