package ensino.emi.api.infra.gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import ensino.emi.api.infra.exception.ServicoExternoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Único ponto do sistema que conversa com a API do Gemini.
 * A chave vai no header (não na URL, para não aparecer em logs) e há timeouts.
 */
@Component
public class GeminiClient {

    private static final Logger log = LoggerFactory.getLogger(GeminiClient.class);

    private final RestClient restClient;
    private final String modelo;

    public GeminiClient(@Value("${gemini.api.key}") String apiKey,
                        @Value("${gemini.api.model:gemini-2.5-flash}") String modelo) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5_000);
        factory.setReadTimeout(30_000);

        this.restClient = RestClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta")
                .defaultHeader("x-goog-api-key", apiKey)
                .requestFactory(factory)
                .build();
        this.modelo = modelo;
    }

    /**
     * @param instrucaoSistema persona/regras da tutora (campo próprio do Gemini, separado da conversa)
     * @param turnos           conversa em ordem cronológica; o último deve ser do usuário
     */
    public String gerarResposta(String instrucaoSistema, List<Turno> turnos) {
        List<Content> contents = turnos.stream()
                .map(t -> new Content(
                        t.papel() == Turno.Papel.MODELO ? "model" : "user",
                        List.of(new Part(t.texto()))))
                .toList();

        Requisicao requisicao = new Requisicao(
                new Content(null, List.of(new Part(instrucaoSistema))),
                contents);

        try {
            Resposta resposta = restClient.post()
                    .uri("/models/{modelo}:generateContent", modelo)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requisicao)
                    .retrieve()
                    .body(Resposta.class);
            return extrairTexto(resposta);
        } catch (RestClientException e) {
            log.error("Falha ao chamar o Gemini", e);
            throw new ServicoExternoException(
                    "A tutora não conseguiu responder agora. Tente novamente em instantes.", e);
        }
    }

    private String extrairTexto(Resposta resposta) {
        if (resposta == null || resposta.candidates() == null || resposta.candidates().isEmpty()) {
            throw new ServicoExternoException("A tutora não pôde responder a essa mensagem.", null);
        }
        Content content = resposta.candidates().get(0).content();
        if (content == null || content.parts() == null) {
            throw new ServicoExternoException("A tutora não pôde responder a essa mensagem.", null);
        }
        String texto = content.parts().stream()
                .map(Part::text)
                .filter(Objects::nonNull)
                .collect(Collectors.joining());
        if (texto.isBlank()) {
            throw new ServicoExternoException("A tutora não pôde responder a essa mensagem.", null);
        }
        return texto;
    }

    // ---- formato JSON da API do Gemini ----

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Requisicao(Content systemInstruction, List<Content> contents) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Content(String role, List<Part> parts) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Part(String text) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Resposta(List<Candidato> candidates) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Candidato(Content content) {
    }
}
