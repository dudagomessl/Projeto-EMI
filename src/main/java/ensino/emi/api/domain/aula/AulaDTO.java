package ensino.emi.api.domain.aula;

public record AulaDTO(
        String nomeAula,
        String previaDoConteudo,
        Long idModulo
) {
}