package ensino.emi.api.domain.usuario;

import ensino.emi.api.domain.chat.SessaoChat;
import ensino.emi.api.domain.chat.SessaoChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SessaoChatRepository sessaoChatRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, SessaoChatRepository sessaoChatRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoChatRepository = sessaoChatRepository;
    }

    @Transactional
    public Usuario cadastrarUsuario(UsuarioDTO dados) {
        // Cria a sessão de chat padrão para o novo aluno
        SessaoChat sessao = new SessaoChat();
        sessao.setPromptChat("Você é a Mei, uma tutora de ensino médio gamificada, amigável e didática.");
        sessaoChatRepository.save(sessao);

        // Cria o usuário vinculado à sessão
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(dados.nomeUsuario());
        usuario.setEmail(dados.email());
        usuario.setSenha(dados.senha()); // Em produção usaria encoder, mas para testes pode ser texto puro
        usuario.setDataNascimento(dados.dataNascimento());
        usuario.setExperiencia(0);
        usuario.setSessaoChat(sessao);

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado para exclusão.");
        }
        usuarioRepository.deleteById(id);
    }

    public List<DadosListagemUsuario> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(usuario -> new DadosListagemUsuario(
                        usuario.getIdUsuario(),
                        usuario.getNomeUsuario(),
                        usuario.getEmail(),
                        usuario.getDataNascimento(),
                        usuario.getExperiencia() // ajuste conforme os campos reais do seu DTO
                ))
                .collect(Collectors.toList());
    }
}