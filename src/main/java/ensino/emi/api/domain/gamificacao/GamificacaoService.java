package ensino.emi.api.domain.gamificacao;

import ensino.emi.api.domain.aula.AulaConcluidaRepository;
import ensino.emi.api.domain.desafio.TentativaDesafioRepository;
import ensino.emi.api.domain.insignia.Insignia;
import ensino.emi.api.domain.insignia.InsigniaResponseDTO;
import ensino.emi.api.domain.missao.Missao;
import ensino.emi.api.domain.missao.MissaoRepository;
import ensino.emi.api.domain.missao.UsuarioMissao;
import ensino.emi.api.domain.missao.UsuarioMissaoId;
import ensino.emi.api.domain.missao.UsuarioMissaoRepository;
import ensino.emi.api.domain.usuario.Usuario;
import ensino.emi.api.domain.usuario.UsuarioInsignia;
import ensino.emi.api.domain.usuario.UsuarioInsigniaId;
import ensino.emi.api.domain.usuario.UsuarioInsigniaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Motor de gamificação. É chamado depois de cada evento (aula concluída, desafio aprovado)
 * e verifica se alguma missão foi cumprida. O progresso nunca é guardado: é uma contagem
 * feita na hora, então não existe risco de ficar desatualizado.
 */
@Service
public class GamificacaoService {

    private final MissaoRepository missaoRepository;
    private final UsuarioMissaoRepository usuarioMissaoRepository;
    private final UsuarioInsigniaRepository usuarioInsigniaRepository;
    private final AulaConcluidaRepository aulaConcluidaRepository;
    private final TentativaDesafioRepository tentativaDesafioRepository;

    public GamificacaoService(MissaoRepository missaoRepository,
                              UsuarioMissaoRepository usuarioMissaoRepository,
                              UsuarioInsigniaRepository usuarioInsigniaRepository,
                              AulaConcluidaRepository aulaConcluidaRepository,
                              TentativaDesafioRepository tentativaDesafioRepository) {
        this.missaoRepository = missaoRepository;
        this.usuarioMissaoRepository = usuarioMissaoRepository;
        this.usuarioInsigniaRepository = usuarioInsigniaRepository;
        this.aulaConcluidaRepository = aulaConcluidaRepository;
        this.tentativaDesafioRepository = tentativaDesafioRepository;
    }

    /**
     * Cumpre as missões que o usuário acabou de alcançar: registra, paga o XP e concede a insígnia.
     * Deve ser chamado DENTRO da transação do evento, para tudo ser gravado junto.
     */
    @Transactional
    public List<MissaoConcluidaDTO> avaliarMissoes(Usuario usuario) {
        Set<Long> jaConcluidas = new HashSet<>(
                usuarioMissaoRepository.findIdsMissoesConcluidas(usuario.getIdUsuario()));

        List<MissaoConcluidaDTO> novasConclusoes = new ArrayList<>();
        for (Missao missao : missaoRepository.findAll()) {
            if (jaConcluidas.contains(missao.getIdMissao())) {
                continue;
            }
            if (calcularProgresso(missao, usuario.getIdUsuario()) >= missao.getMeta()) {
                novasConclusoes.add(conceder(usuario, missao));
            }
        }
        return novasConclusoes;
    }

    /** Quanto o usuário já avançou na missão (ex.: 3 aulas concluídas). */
    @Transactional(readOnly = true)
    public long calcularProgresso(Missao missao, Long idUsuario) {
        Long idModulo = missao.getModulo() != null ? missao.getModulo().getIdModulo() : null;

        return switch (missao.getTipo()) {
            case AULAS_CONCLUIDAS -> idModulo == null
                    ? aulaConcluidaRepository.countByUsuarioIdUsuario(idUsuario)
                    : aulaConcluidaRepository.countByUsuarioIdUsuarioAndAulaModuloIdModulo(idUsuario, idModulo);
            case DESAFIOS_APROVADOS -> idModulo == null
                    ? tentativaDesafioRepository.contarDesafiosAprovados(idUsuario)
                    : tentativaDesafioRepository.contarDesafiosAprovadosNoModulo(idUsuario, idModulo);
        };
    }

    private MissaoConcluidaDTO conceder(Usuario usuario, Missao missao) {
        UsuarioMissao conclusao = new UsuarioMissao();
        conclusao.setId(new UsuarioMissaoId(usuario.getIdUsuario(), missao.getIdMissao()));
        conclusao.setUsuario(usuario);
        conclusao.setMissao(missao);
        usuarioMissaoRepository.save(conclusao);

        usuario.adicionarXp(missao.getRecompensaXp());

        InsigniaResponseDTO insigniaDTO = null;
        Insignia insignia = missao.getInsigniaRecompensa();
        if (insignia != null) {
            UsuarioInsigniaId idInsignia = new UsuarioInsigniaId(usuario.getIdUsuario(), insignia.getIdInsignia());
            if (!usuarioInsigniaRepository.existsById(idInsignia)) {
                UsuarioInsignia ui = new UsuarioInsignia();
                ui.setId(idInsignia);
                ui.setUsuario(usuario);
                ui.setInsignia(insignia);
                usuarioInsigniaRepository.save(ui);
            }
            insigniaDTO = InsigniaResponseDTO.de(insignia);
        }

        return new MissaoConcluidaDTO(missao.getIdMissao(), missao.getTitulo(), missao.getRecompensaXp(), insigniaDTO);
    }
}
