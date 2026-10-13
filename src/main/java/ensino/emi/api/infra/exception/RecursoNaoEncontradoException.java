package ensino.emi.api.infra.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(recurso + " não encontrado(a) (id " + id + ").");
    }

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
