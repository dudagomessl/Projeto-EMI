package ensino.emi.api.infra.exception;

/** Falha em um serviço externo (ex.: Gemini). Vira HTTP 502. */
public class ServicoExternoException extends RuntimeException {

    public ServicoExternoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
