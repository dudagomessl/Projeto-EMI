package ensino.emi.api.infra.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroResposta(int status, String erro, Map<String, String> campos) {

    public ErroResposta(int status, String erro) {
        this(status, erro, null);
    }
}
