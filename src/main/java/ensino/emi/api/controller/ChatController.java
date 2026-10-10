package ensino.emi.api.controller;

import ensino.emi.api.domain.chat.ChatService;
import ensino.emi.api.domain.chat.ChatService.RespostaChatDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/{idChat}/enviar")
    public ResponseEntity<RespostaChatDTO> enviarMensagem(
            @PathVariable Long idChat,
            @RequestBody Map<String, String> payload) {

        String mensagem = payload.get("mensagem");
        RespostaChatDTO resposta = chatService.processarMensagem(idChat, mensagem);

        return ResponseEntity.ok(resposta);
    }
}