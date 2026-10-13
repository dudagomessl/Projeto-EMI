package ensino.emi.api.domain.modulo;

public record ModuloResponseDTO(Long idModulo, String nomeModulo, String descricao, Long idTrilha) {

    public static ModuloResponseDTO de(Modulo m) {
        return new ModuloResponseDTO(m.getIdModulo(), m.getNomeModulo(), m.getDescricao(),
                m.getTrilha().getIdTrilha());
    }
}
