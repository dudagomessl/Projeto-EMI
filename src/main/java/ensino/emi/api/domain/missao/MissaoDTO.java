package ensino.emi.api.domain.missao;

public record MissaoDTO(
        String titulo, // Se no JSON do Insomnia você manda "titulo"
        String descricao,
        Integer recompensaXp,
        Long idInsigniaRecompensa
) {
}