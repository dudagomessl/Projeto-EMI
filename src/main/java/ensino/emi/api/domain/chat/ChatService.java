package ensino.emi.api.domain.chat;

import ensino.emi.api.domain.usuario.Usuario;
import ensino.emi.api.domain.usuario.UsuarioRepository;
import ensino.emi.api.infra.exception.RecursoNaoEncontradoException;
import ensino.emi.api.infra.gemini.GeminiClient;
import ensino.emi.api.infra.gemini.Turno;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Propositalmente SEM @Transactional na classe: a chamada HTTP ao Gemini pode levar segundos
 * e não deve manter uma conexão do banco aberta. Cada acesso ao banco abre a sua própria transação.
 */
@Service
public class ChatService {

    /** Quantas mensagens anteriores são reenviadas ao Gemini a cada pergunta. */
    private static final int MAX_MENSAGENS_CONTEXTO = 20;
    private static final int MAX_MENSAGENS_HISTORICO = 50;

    private final UsuarioRepository usuarioRepository;
    private final MensagemChatRepository mensagemChatRepository;
    private final GeminiClient geminiClient;

    public ChatService(UsuarioRepository usuarioRepository,
                       MensagemChatRepository mensagemChatRepository,
                       GeminiClient geminiClient) {
        this.usuarioRepository = usuarioRepository;
        this.mensagemChatRepository = mensagemChatRepository;
        this.geminiClient = geminiClient;
    }

    public RespostaChatDTO enviarMensagem(Long idUsuario, String textoAluno) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", idUsuario));
        SessaoChat sessao = buscarSessao(idUsuario);

        List<Turno> conversa = montarConversa(sessao.getIdChat(), textoAluno);
        String respostaTutora = geminiClient.gerarResposta(sessao.getPromptChat(), conversa);

        // Só salva depois que o Gemini respondeu: se falhar, o histórico não fica "torto"
        mensagemChatRepository.saveAll(List.of(
                MensagemChat.builder().sessaoChat(sessao).remetente(Remetente.ALUNO).mensagem(textoAluno).build(),
                MensagemChat.builder().sessaoChat(sessao).remetente(Remetente.TUTORA).mensagem(respostaTutora).build()
        ));

        return new RespostaChatDTO(respostaTutora, usuario.getExperiencia(),
                usuario.getXpNoNivelAtual(), usuario.getNivelAtual());
    }

    /** Últimas mensagens da conversa, em ordem cronológica (para reabrir o chat no front). */
    public List<MensagemChatDTO> historico(Long idUsuario) {
        SessaoChat sessao = buscarSessao(idUsuario);
        List<MensagemChat> mensagens = new ArrayList<>(
                mensagemChatRepository.findBySessaoChatIdChatOrderByIdMensagemDesc(
                        sessao.getIdChat(), PageRequest.of(0, MAX_MENSAGENS_HISTORICO)));
        Collections.reverse(mensagens);
        return mensagens.stream().map(MensagemChatDTO::de).toList();
    }

    private SessaoChat buscarSessao(Long idUsuario) {
        return usuarioRepository.buscarSessaoChat(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sessão de chat do usuário", idUsuario));
    }

    private List<Turno> montarConversa(Long idChat, String textoAluno) {
        List<MensagemChat> recentes = new ArrayList<>(
                mensagemChatRepository.findBySessaoChatIdChatOrderByIdMensagemDesc(
                        idChat, PageRequest.of(0, MAX_MENSAGENS_CONTEXTO)));
        Collections.reverse(recentes);

        // O contexto cortado precisa começar numa fala do aluno
        while (!recentes.isEmpty() && recentes.get(0).getRemetente() == Remetente.TUTORA) {
            recentes.remove(0);
        }

        List<Turno> turnos = new ArrayList<>();
        for (MensagemChat m : recentes) {
            Turno.Papel papel = m.getRemetente() == Remetente.ALUNO ? Turno.Papel.USUARIO : Turno.Papel.MODELO;
            turnos.add(new Turno(papel, m.getMensagem()));
        }
        turnos.add(new Turno(Turno.Papel.USUARIO, textoAluno));
        return turnos;
    }
}
