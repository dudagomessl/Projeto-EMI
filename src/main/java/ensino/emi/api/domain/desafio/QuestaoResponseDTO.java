package ensino.emi.api.domain.desafio;

/** Questão como o aluno vê: SEM o gabarito. */
public record QuestaoResponseDTO(
        Long idQuestao,
        String enunciado,
        String alternativaA,
        String alternativaB,
        String alternativaC,
        String alternativaD
) {
    public static QuestaoResponseDTO de(Questao q) {
        return new QuestaoResponseDTO(q.getIdQuestao(), q.getEnunciado(),
                q.getAlternativaA(), q.getAlternativaB(), q.getAlternativaC(), q.getAlternativaD());
    }
}
