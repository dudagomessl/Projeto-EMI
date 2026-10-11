package ensino.emi.api.domain.conteudo;

public record ConteudoDTO(
        Long idAula,
        String tituloSecao,
        String corpoTexto,
        Integer ordem
) {
}