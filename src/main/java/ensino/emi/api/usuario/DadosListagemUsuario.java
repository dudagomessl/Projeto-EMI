package ensino.emi.api.usuario;

public record DadosListagemUsuario(String nome, int nivelDaConta, double notaMedia, MateriaFavorita materiaFavorita) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getNome(), usuario.getNivelDaConta(), usuario.getNotaMedia(), usuario.getMateriaFavorita());
    }
}
