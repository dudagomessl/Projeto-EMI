package ensino.emi.api.domain.desafio;

import ensino.emi.api.domain.aula.AulaConcluidaRepository;
import ensino.emi.api.domain.aula.AulaRepository;
import ensino.emi.api.domain.gamificacao.GamificacaoService;
import ensino.emi.api.domain.gamificacao.MissaoConcluidaDTO;
import ensino.emi.api.domain.modulo.Modulo;
import ensino.emi.api.domain.modulo.ModuloRepository;
import ensino.emi.api.domain.usuario.Usuario;
import ensino.emi.api.domain.usuario.UsuarioRepository;
import ensino.emi.api.infra.exception.ConflitoException;
import ensino.emi.api.infra.exception.RecursoNaoEncontradoException;
import ensino.emi.api.infra.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final QuestaoRepository questaoRepository;
    private final TentativaDesafioRepository tentativaRepository;
    private final ModuloRepository moduloRepository;
    private final AulaRepository aulaRepository;
    private final AulaConcluidaRepository aulaConcluidaRepository;
    private final UsuarioRepository usuarioRepository;
    private final GamificacaoService gamificacaoService;

    public DesafioService(DesafioRepository desafioRepository,
                          QuestaoRepository questaoRepository,
                          TentativaDesafioRepository tentativaRepository,
                          ModuloRepository moduloRepository,
                          AulaRepository aulaRepository,
                          AulaConcluidaRepository aulaConcluidaRepository,
                          UsuarioRepository usuarioRepository,
                          GamificacaoService gamificacaoService) {
        this.desafioRepository = desafioRepository;
        this.questaoRepository = questaoRepository;
        this.tentativaRepository = tentativaRepository;
        this.moduloRepository = moduloRepository;
        this.aulaRepository = aulaRepository;
        this.aulaConcluidaRepository = aulaConcluidaRepository;
        this.usuarioRepository = usuarioRepository;
        this.gamificacaoService = gamificacaoService;
    }

    public List<DesafioResumoDTO> listar(Long idModulo) {
        List<Desafio> desafios = idModulo == null
                ? desafioRepository.findAll()
                : desafioRepository.findByModuloIdModuloOrderByOrdemAsc(idModulo);
        return desafios.stream().map(DesafioResumoDTO::de).toList();
    }

    public DesafioResponseDTO buscar(Long id) {
        Desafio desafio = buscarEntidade(id);
        return DesafioResponseDTO.de(desafio, questaoRepository.findByDesafioIdDesafioOrderByIdQuestaoAsc(id));
    }

    @Transactional
    public DesafioResponseDTO cadastrar(DesafioDTO dados) {
        Modulo modulo = moduloRepository.findById(dados.idModulo())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Módulo", dados.idModulo()));
        validarOrdem(dados.idModulo(), dados.ordem(), null);

        Desafio desafio = new Desafio();
        desafio.setModulo(modulo);
        desafio.setNomeDesafio(dados.nomeDesafio());
        desafio.setNotaDeCorte(dados.notaDeCorte());
        desafio.setRecompensaXp(dados.recompensaXp());
        desafio.setOrdem(dados.ordem());
        desafioRepository.save(desafio);

        List<Questao> questoes = questaoRepository.saveAll(
                dados.questoes().stream().map(q -> novaQuestao(desafio, q)).toList());

        return DesafioResponseDTO.de(desafio, questoes);
    }

    @Transactional
    public DesafioResumoDTO atualizar(Long id, DesafioAtualizacaoDTO dados) {
        Desafio desafio = buscarEntidade(id);
        validarOrdem(desafio.getModulo().getIdModulo(), dados.ordem(), id);

        desafio.setNomeDesafio(dados.nomeDesafio());
        desafio.setNotaDeCorte(dados.notaDeCorte());
        desafio.setRecompensaXp(dados.recompensaXp());
        desafio.setOrdem(dados.ordem());
        return DesafioResumoDTO.de(desafio);
    }

    @Transactional
    public void deletar(Long id) {
        desafioRepository.delete(buscarEntidade(id));
    }

    /**
     * Regra de desbloqueio: o desafio libera quando o usuário concluiu todas as aulas
     * do módulo com ordem menor que a do desafio.
     */
    public boolean estaLiberado(Desafio desafio, Long idUsuario) {
        Long idModulo = desafio.getModulo().getIdModulo();
        long exigidas = aulaRepository.countByModuloIdModuloAndOrdemLessThan(idModulo, desafio.getOrdem());
        long concluidas = aulaConcluidaRepository.contarConcluidasAntesDaOrdem(idUsuario, idModulo, desafio.getOrdem());
        return concluidas >= exigidas;
    }

    /**
     * Corrige o desafio no servidor (o gabarito nunca sai do back-end), grava a tentativa
     * e paga o XP apenas na PRIMEIRA aprovação do usuário nesse desafio.
     */
    @Transactional
    public ResultadoDesafioDTO responder(Long idDesafio, RespostaDesafioDTO dados) {
        Desafio desafio = buscarEntidade(idDesafio);
        Usuario usuario = usuarioRepository.findById(dados.idUsuario())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", dados.idUsuario()));

        if (!estaLiberado(desafio, usuario.getIdUsuario())) {
            throw new RegraNegocioException("Conclua as aulas anteriores para liberar este desafio.");
        }

        List<Questao> questoes = questaoRepository.findByDesafioIdDesafioOrderByIdQuestaoAsc(idDesafio);
        if (questoes.isEmpty()) {
            throw new RegraNegocioException("Este desafio ainda não possui questões.");
        }

        // Se o front mandar a mesma questão duas vezes, vale a última resposta
        Map<Long, Alternativa> respostas = dados.respostas().stream()
                .collect(Collectors.toMap(RespostaQuestaoDTO::idQuestao, RespostaQuestaoDTO::resposta, (a, b) -> b));

        int acertos = 0;
        List<Long> erradas = new ArrayList<>();
        for (Questao questao : questoes) {
            if (questao.getRespostaCorreta() == respostas.get(questao.getIdQuestao())) {
                acertos++;
            } else {
                erradas.add(questao.getIdQuestao());   // não respondida também conta como errada
            }
        }

        int nota = acertos * 100 / questoes.size();
        boolean aprovado = nota >= desafio.getNotaDeCorte();

        // Precisa ser verificado ANTES de gravar a tentativa atual
        boolean primeiraAprovacao = aprovado && !tentativaRepository
                .existsByUsuarioIdUsuarioAndDesafioIdDesafioAndAprovadoTrue(usuario.getIdUsuario(), idDesafio);

        TentativaDesafio tentativa = new TentativaDesafio();
        tentativa.setUsuario(usuario);
        tentativa.setDesafio(desafio);
        tentativa.setNota(nota);
        tentativa.setAprovado(aprovado);
        tentativaRepository.save(tentativa);

        int xpGanho = 0;
        List<MissaoConcluidaDTO> missoes = List.of();
        if (primeiraAprovacao) {
            xpGanho = desafio.getRecompensaXp();
            usuario.adicionarXp(xpGanho);
            missoes = gamificacaoService.avaliarMissoes(usuario);
        }

        return new ResultadoDesafioDTO(nota, aprovado, acertos, questoes.size(), erradas, xpGanho,
                usuario.getExperiencia(), usuario.getXpNoNivelAtual(), usuario.getNivelAtual(), missoes);
    }

    private Questao novaQuestao(Desafio desafio, QuestaoDTO dto) {
        Questao questao = new Questao();
        questao.setDesafio(desafio);
        questao.setEnunciado(dto.enunciado());
        questao.setAlternativaA(dto.alternativaA());
        questao.setAlternativaB(dto.alternativaB());
        questao.setAlternativaC(dto.alternativaC());
        questao.setAlternativaD(dto.alternativaD());
        questao.setRespostaCorreta(dto.respostaCorreta());
        return questao;
    }

    /** Aulas e desafios dividem a mesma linha: a ordem não pode se repetir dentro do módulo. */
    private void validarOrdem(Long idModulo, Integer ordem, Long idDesafioAtual) {
        boolean ocupadaPorDesafio = idDesafioAtual == null
                ? desafioRepository.existsByModuloIdModuloAndOrdem(idModulo, ordem)
                : desafioRepository.existsByModuloIdModuloAndOrdemAndIdDesafioNot(idModulo, ordem, idDesafioAtual);

        if (ocupadaPorDesafio || aulaRepository.existsByModuloIdModuloAndOrdem(idModulo, ordem)) {
            throw new ConflitoException("Já existe uma aula ou desafio com a ordem " + ordem + " neste módulo.");
        }
    }

    private Desafio buscarEntidade(Long id) {
        return desafioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Desafio", id));
    }
}
