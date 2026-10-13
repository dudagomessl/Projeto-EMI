package ensino.emi.api.domain.aula;

import ensino.emi.api.domain.desafio.DesafioRepository;
import ensino.emi.api.domain.gamificacao.GamificacaoService;
import ensino.emi.api.domain.gamificacao.MissaoConcluidaDTO;
import ensino.emi.api.domain.modulo.Modulo;
import ensino.emi.api.domain.modulo.ModuloRepository;
import ensino.emi.api.domain.usuario.Usuario;
import ensino.emi.api.domain.usuario.UsuarioRepository;
import ensino.emi.api.infra.exception.ConflitoException;
import ensino.emi.api.infra.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AulaService {

    public static final int XP_POR_AULA = 250;

    private final AulaRepository aulaRepository;
    private final AulaConcluidaRepository aulaConcluidaRepository;
    private final ModuloRepository moduloRepository;
    private final UsuarioRepository usuarioRepository;
    private final DesafioRepository desafioRepository;
    private final GamificacaoService gamificacaoService;

    public AulaService(AulaRepository aulaRepository,
                       AulaConcluidaRepository aulaConcluidaRepository,
                       ModuloRepository moduloRepository,
                       UsuarioRepository usuarioRepository,
                       DesafioRepository desafioRepository,
                       GamificacaoService gamificacaoService) {
        this.aulaRepository = aulaRepository;
        this.aulaConcluidaRepository = aulaConcluidaRepository;
        this.moduloRepository = moduloRepository;
        this.usuarioRepository = usuarioRepository;
        this.desafioRepository = desafioRepository;
        this.gamificacaoService = gamificacaoService;
    }

    public List<AulaResumoDTO> listar(Long idModulo) {
        List<Aula> aulas = idModulo == null
                ? aulaRepository.findAll()
                : aulaRepository.findByModuloIdModuloOrderByOrdemAsc(idModulo);
        return aulas.stream().map(AulaResumoDTO::de).toList();
    }

    public AulaResponseDTO buscar(Long id) {
        return AulaResponseDTO.de(buscarEntidade(id));
    }

    @Transactional
    public AulaResponseDTO cadastrar(AulaDTO dados) {
        Aula aula = new Aula();
        preencher(aula, dados, null);
        return AulaResponseDTO.de(aulaRepository.save(aula));
    }

    @Transactional
    public AulaResponseDTO atualizar(Long id, AulaDTO dados) {
        Aula aula = buscarEntidade(id);
        preencher(aula, dados, id);
        return AulaResponseDTO.de(aula);
    }

    @Transactional
    public void deletar(Long id) {
        aulaRepository.delete(buscarEntidade(id));
    }

    /**
     * Marca a aula como concluída para o usuário. Só paga XP na primeira vez.
     * Depois do XP, avalia se alguma missão foi cumprida.
     */
    @Transactional
    public ResultadoConclusaoDTO concluirAula(Long idUsuario, Long idAula) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", idUsuario));
        Aula aula = buscarEntidade(idAula);

        if (aulaConcluidaRepository.existsByUsuarioIdUsuarioAndAulaIdAula(idUsuario, idAula)) {
            return new ResultadoConclusaoDTO("Aula já concluída anteriormente.", 0,
                    usuario.getExperiencia(), usuario.getXpNoNivelAtual(), usuario.getNivelAtual(), List.of());
        }

        AulaConcluida conclusao = new AulaConcluida();
        conclusao.setId(new AulaConcluidaId(idUsuario, idAula));
        conclusao.setUsuario(usuario);
        conclusao.setAula(aula);
        conclusao.setDataConclusao(LocalDateTime.now());
        aulaConcluidaRepository.save(conclusao);

        // O usuário é uma entidade gerenciada: a alteração é gravada sozinha no fim da transação
        usuario.adicionarXp(XP_POR_AULA);

        List<MissaoConcluidaDTO> missoes = gamificacaoService.avaliarMissoes(usuario);

        return new ResultadoConclusaoDTO("Aula concluída com sucesso!", XP_POR_AULA,
                usuario.getExperiencia(), usuario.getXpNoNivelAtual(), usuario.getNivelAtual(), missoes);
    }

    private void preencher(Aula aula, AulaDTO dados, Long idAulaAtual) {
        Modulo modulo = moduloRepository.findById(dados.idModulo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Módulo", dados.idModulo()));
        validarOrdem(dados.idModulo(), dados.ordem(), idAulaAtual);

        aula.setModulo(modulo);
        aula.setNomeAula(dados.nomeAula());
        aula.setPreviaDoConteudo(dados.previaDoConteudo());
        aula.setTexto(dados.texto());
        aula.setVideoUrl(dados.videoUrl());
        aula.setOrdem(dados.ordem());
    }

    /** Aulas e desafios dividem a mesma linha: a ordem não pode se repetir dentro do módulo. */
    private void validarOrdem(Long idModulo, Integer ordem, Long idAulaAtual) {
        boolean ocupadaPorAula = idAulaAtual == null
                ? aulaRepository.existsByModuloIdModuloAndOrdem(idModulo, ordem)
                : aulaRepository.existsByModuloIdModuloAndOrdemAndIdAulaNot(idModulo, ordem, idAulaAtual);

        if (ocupadaPorAula || desafioRepository.existsByModuloIdModuloAndOrdem(idModulo, ordem)) {
            throw new ConflitoException("Já existe uma aula ou desafio com a ordem " + ordem + " neste módulo.");
        }
    }

    private Aula buscarEntidade(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aula", id));
    }
}
