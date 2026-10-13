package ensino.emi.api.domain.trilha;

public record TrilhaResponseDTO(Long idTrilha, String nomeTrilha) {

    public static TrilhaResponseDTO de(Trilha t) {
        return new TrilhaResponseDTO(t.getIdTrilha(), t.getNomeTrilha());
    }
}
