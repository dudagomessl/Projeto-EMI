package ensino.emi.api.controller;

import ensino.emi.api.domain.chat.ChatService;
import ensino.emi.api.domain.chat.EnviarMensagemDTO;
import ensino.emi.api.domain.chat.MensagemChatDTO;
import ensino.emi.api.domain.chat.RespostaChatDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Cada usuário tem uma única sessão de chat, então não há id de chat na URL
 * (nada para adivinhar). O idUsuario é TEMPORÁRIO: virá do token JWT.
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/mensagens")
    public ResponseEntity<RespostaChatDTO> enviar(@RequestBody @Valid EnviarMensagemDTO dados) {
        return ResponseEntity.ok(chatService.enviarMensagem(dados.idUsuario(), dados.mensagem()));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<MensagemChatDTO>> historico(@RequestParam Long idUsuario) {
        return ResponseEntity.ok(chatService.historico(idUsuario));
    }
}
