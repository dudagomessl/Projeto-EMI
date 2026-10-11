package ensino.emi.api.domain.insignia;

public record InsigniaDTO(
        String nomeInsignia,
        String descricao,
        String iconeUrl,
        RaridadeInsignia raridade // Deve estar presente aqui
) {
}