package ensino.emi.api.domain.chat;

import ensino.emi.api.domain.usuario.Usuario;
import ensino.emi.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final SessaoChatRepository sessaoChatRepository;
    private final MensagemChatRepository mensagemChatRepository;
    private final UsuarioRepository usuarioRepository;
    private final RestClient restClient;

    public ChatService(SessaoChatRepository sessaoChatRepository,
                       MensagemChatRepository mensagemChatRepository,
                       UsuarioRepository usuarioRepository) {
        this.sessaoChatRepository = sessaoChatRepository;
        this.mensagemChatRepository = mensagemChatRepository;
        this.usuarioRepository = usuarioRepository;
        this.restClient = RestClient.create();
    }

    @Transactional
    public RespostaChatDTO processarMensagem(Long idChat, String mensagemAluno) {
        SessaoChat sessao = sessaoChatRepository.findById(idChat)
                .orElseThrow(() -> new RuntimeException("Sessão de Chat não encontrada!"));

        List<MensagemChat> historico = mensagemChatRepository.findBySessaoChatOrderByIdMensagemAsc(sessao);

        StringBuilder conversaCompleta = new StringBuilder();

        if (historico.isEmpty()) {
            conversaCompleta.append(sessao.getPromptChat())
                    .append("\n\n [INICIO DA SESSÃO] Pergunta do Aluno: ")
                    .append(mensagemAluno);
        } else {
            for (MensagemChat msg : historico) {
                if ("ALUNO".equalsIgnoreCase(msg.getRemetente())) {
                    conversaCompleta.append("\nAluno: ").append(msg.getMensagem());
                } else {
                    conversaCompleta.append("\nTutora: ").append(msg.getMensagem());
                }
            }
            conversaCompleta.append("\nAluno: ").append(mensagemAluno);
        }

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        var requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", conversaCompleta.toString())))
                )
        );

        Map response = restClient.post()
                .uri(url)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        String respostaTutora = extrairTextoResposta(response);

        MensagemChat msgAluno = MensagemChat.builder()
                .sessaoChat(sessao)
                .remetente("ALUNO")
                .mensagem(mensagemAluno)
                .build();

        MensagemChat msgTutora = MensagemChat.builder()
                .sessaoChat(sessao)
                .remetente("TUTORA")
                .mensagem(respostaTutora)
                .build();

        mensagemChatRepository.save(msgAluno);
        mensagemChatRepository.save(msgTutora);

        Usuario usuario = usuarioRepository.findBySessaoChatIdChat(idChat).orElse(null);

        return new RespostaChatDTO(
                respostaTutora,
                usuario != null ? usuario.getExperiencia() : 0,
                usuario != null ? usuario.getXpNoNivelAtual() : 0,
                usuario != null ? usuario.getNivelAtual() : 1
        );
    }

    @SuppressWarnings("unchecked")
    private String extrairTextoResposta(Map response) {
        try {
            List candidates = (List) response.get("candidates");
            Map firstCandidate = (Map) candidates.get(0);
            Map content = (Map) firstCandidate.get("content");
            List parts = (List) content.get("parts");
            Map firstPart = (Map) parts.get(0);
            return (String) firstPart.get("text");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar resposta do Gemini", e);
        }
    }

    public record RespostaChatDTO(String resposta, Integer xpTotal, Integer xpAtualNivel, Integer nivelAtual) {
    }
}