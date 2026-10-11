package ensino.emi.api.domain.desafio;

public record DesafioDTO(
        Long idAula,
        String nomeDesafio,
        String promptDesafio, // <--- Novo campo
        String perguntaEnunciado,
        String respostaCorreta,
        Integer recompensaXp,
        Integer notaDeCorte
) {
}