package ensino.emi.api.domain.missao;

import ensino.emi.api.domain.gamificacao.GamificacaoService;
import ensino.emi.api.domain.insignia.Insignia;
import ensino.emi.api.domain.insignia.InsigniaRepository;
import ensino.emi.api.domain.modulo.Modulo;
import ensino.emi.api.domain.modulo.ModuloRepository;
import ensino.emi.api.domain.usuario.UsuarioRepository;
import ensino.emi.api.infra.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MissaoService {

    private final MissaoRepository missaoRepository;
    private final UsuarioMissaoRepository usuarioMissaoRepository;
    private final ModuloRepository moduloRepository;
    private final InsigniaRepository insigniaRepository;
    private final UsuarioRepository usuarioRepository;
    private final GamificacaoService gamificacaoService;

    public MissaoService(MissaoRepository missaoRepository,
                         UsuarioMissaoRepository usuarioMissaoRepository,
                         ModuloRepository moduloRepository,
                         InsigniaRepository insigniaRepository,
                         UsuarioRepository usuarioRepository,
                         GamificacaoService gamificacaoService) {
        this.missaoRepository = missaoRepository;
        this.usuarioMissaoRepository = usuarioMissaoRepository;
        this.moduloRepository = moduloRepository;
        this.insigniaRepository = insigniaRepository;
        this.usuarioRepository = usuarioRepository;
        this.gamificacaoService = gamificacaoService;
    }

    public List<MissaoResponseDTO> listar() {
        return missaoRepository.findAll().stream().map(MissaoResponseDTO::de).toList();
    }

    public MissaoResponseDTO buscar(Long id) {
        return MissaoResponseDTO.de(buscarEntidade(id));
    }

    @Transactional
    public MissaoResponseDTO cadastrar(MissaoDTO dados) {
        Missao missao = new Missao();
        preencher(missao, dados);
        return MissaoResponseDTO.de(missaoRepository.save(missao));
    }

    @Transactional
    public MissaoResponseDTO atualizar(Long id, MissaoDTO dados) {
        Missao missao = buscarEntidade(id);
        preencher(missao, dados);
        return MissaoResponseDTO.de(missao);
    }

    @Transactional
    public void deletar(Long id) {
        missaoRepository.delete(buscarEntidade(id));
    }

    /** Todas as missões com o progresso do usuário (concluídas e em andamento). */
    public List<MissaoProgressoDTO> listarDoUsuario(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RecursoNaoEncontradoException("Usuário", idUsuario);
        }

        Map<Long, LocalDateTime> conquistas = usuarioMissaoRepository.findByUsuarioIdUsuario(idUsuario).stream()
                .collect(Collectors.toMap(um -> um.getMissao().getIdMissao(), UsuarioMissao::getDataConquista));

        return missaoRepository.findAll().stream().map(missao -> {
            LocalDateTime data = conquistas.get(missao.getIdMissao());
            boolean concluida = data != null;
            int progresso = concluida
                    ? missao.getMeta()
                    : (int) Math.min(gamificacaoService.calcularProgresso(missao, idUsuario), missao.getMeta());
            return MissaoProgressoDTO.de(missao, progresso, concluida, data);
        }).toList();
    }

    private void preencher(Missao missao, MissaoDTO dados) {
        Modulo modulo = null;
        if (dados.idModulo() != null) {
            modulo = moduloRepository.findById(dados.idModulo())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Módulo", dados.idModulo()));
        }
        Insignia insignia = null;
        if (dados.idInsigniaRecompensa() != null) {
            insignia = insigniaRepository.findById(dados.idInsigniaRecompensa())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Insígnia", dados.idInsigniaRecompensa()));
        }

        missao.setTitulo(dados.titulo());
        missao.setDescricao(dados.descricao());
        missao.setTipo(dados.tipo());
        missao.setMeta(dados.meta());
        missao.setModulo(modulo);
        missao.setRecompensaXp(dados.recompensaXp());
        missao.setInsigniaRecompensa(insignia);
    }

    private Missao buscarEntidade(Long id) {
        return missaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Missão", id));
    }
}
