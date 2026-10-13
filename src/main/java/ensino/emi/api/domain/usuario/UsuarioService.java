package ensino.emi.api.domain.usuario;

import ensino.emi.api.domain.chat.SessaoChat;
import ensino.emi.api.domain.insignia.InsigniaResponseDTO;
import ensino.emi.api.infra.exception.ConflitoException;
import ensino.emi.api.infra.exception.CredenciaisInvalidasException;
import ensino.emi.api.infra.exception.RecursoNaoEncontradoException;
import org.springframework.data.domain.Sort;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

//    private final UsuarioRepository usuarioRepository;
//    private final UsuarioInsigniaRepository usuarioInsigniaRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UsuarioService(UsuarioRepository usuarioRepository,
//                          UsuarioInsigniaRepository usuarioInsigniaRepository,
//                          PasswordEncoder passwordEncoder) {
//        this.usuarioRepository = usuarioRepository;
//        this.usuarioInsigniaRepository = usuarioInsigniaRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    private final UsuarioRepository usuarioRepository;
    private final UsuarioInsigniaRepository usuarioInsigniaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioInsigniaRepository usuarioInsigniaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioInsigniaRepository = usuarioInsigniaRepository;
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioDTO dados) {
        String email = normalizarEmail(dados.email());
        if (usuarioRepository.existsByEmail(email)) {
            throw new ConflitoException("E-mail já cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(dados.nomeUsuario().trim());
        usuario.setEmail(email);
        //usuario.setSenha(passwordEncoder.encode(dados.senha()));
        usuario.setSenha(dados.senha());
        usuario.setDataNascimento(dados.dataNascimento());
        usuario.setSessaoChat(SessaoChat.criarPadrao());   // salva junto (cascade)

        return UsuarioResponseDTO.de(usuarioRepository.save(usuario));
    }

    /**
     * Login provisório: confere e-mail/senha e devolve o perfil. Será trocado por JWT.
     */
    public UsuarioResponseDTO autenticar(LoginDTO dados) {
        Usuario usuario = usuarioRepository.findByEmail(normalizarEmail(dados.email()))
                .orElseThrow(CredenciaisInvalidasException::new);

//        if (!passwordEncoder.matches(dados.senha(), usuario.getSenha())) {
//            throw new CredenciaisInvalidasException();
//        }
        if (!dados.senha().equals(usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }
        return UsuarioResponseDTO.de(usuario);
    }

    public UsuarioResponseDTO buscar(Long id) {
        return UsuarioResponseDTO.de(buscarEntidade(id));
    }

    /**
     * Lista pública ordenada por XP (serve como ranking).
     */
    public List<UsuarioResumoDTO> listar() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.DESC, "experiencia"))
                .stream()
                .map(UsuarioResumoDTO::de)
                .toList();
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioAtualizacaoDTO dados) {
        Usuario usuario = buscarEntidade(id);
        usuario.setNomeUsuario(dados.nomeUsuario().trim());
        usuario.setDataNascimento(dados.dataNascimento());
        return UsuarioResponseDTO.de(usuario);
    }

    @Transactional
    public void deletar(Long id) {
        usuarioRepository.delete(buscarEntidade(id));
    }

    public List<InsigniaResponseDTO> listarInsignias(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RecursoNaoEncontradoException("Usuário", idUsuario);
        }
        return usuarioInsigniaRepository.findInsigniasPorUsuario(idUsuario)
                .stream()
                .map(InsigniaResponseDTO::de)
                .toList();
    }

    private Usuario buscarEntidade(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", id));
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }
}
