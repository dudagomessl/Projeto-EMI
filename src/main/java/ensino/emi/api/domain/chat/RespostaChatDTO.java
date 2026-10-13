package ensino.emi.api.domain.chat;

public record RespostaChatDTO(
        String resposta,
        int xpTotal,
        int xpAtualNivel,
        int nivelAtual
) {
}
