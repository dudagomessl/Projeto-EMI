package ensino.emi.api.domain.modulo;

/**
 * Um item da "linha" do módulo (aula ou desafio) com o estado para um usuário.
 * É exatamente o que o front precisa para desenhar a trilha.
 */
public record ItemProgressoDTO(
        TipoItem tipo,
        Long id,
        String nome,
        Integer ordem,
        boolean concluido,
        boolean liberado
) {
}
