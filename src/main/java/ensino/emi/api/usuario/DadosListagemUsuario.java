package ensino.emi.api.usuario;

public record DadosListagemUsuario(Long id, String nome, String email, Integer nivelDaConta, Double notaMedia,
                                   MateriaFavorita materiaFavorita) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getNivelDaConta(), usuario.getNotaMedia(), usuario.getMateriaFavorita());
    }
}
