package ensino.emi.api.infra.exception;

/** Requisição válida, mas que quebra uma regra de negócio (ex.: desafio bloqueado). Vira HTTP 422. */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
