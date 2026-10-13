package ensino.emi.api.infra.exception;

/** Conflito com o estado atual dos dados (ex.: e-mail já cadastrado). Vira HTTP 409. */
public class ConflitoException extends RuntimeException {

    public ConflitoException(String mensagem) {
        super(mensagem);
    }
}
